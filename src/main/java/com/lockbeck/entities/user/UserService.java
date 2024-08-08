package com.lockbeck.entities.user;


import com.lockbeck.demo.Response;
import com.lockbeck.entities.token.Token;
import com.lockbeck.entities.token.TokenRepository;
import com.lockbeck.exceptions.BadRequestException;
import com.lockbeck.exceptions.NotFoundException;
import com.lockbeck.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class   UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final ValidationUtil validationUtil;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPsw(), user.getPassword())) {
            throw new BadRequestException("Eski parolingiz noto'g'ri");
        }
        // check if the two new passwords are the same
        if (!request.getNewPsw().equals(request.getConfirmPsw())) {
            throw new BadRequestException("Parollar mos emas");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPsw()));

        // save the new password
        userRepository.save(user);
    }
    public Response changePswAdmin(ChangePasswordRequestAdmin requestAdmin,Integer userId){
        UserEntity entity = get(userId);
        entity.setPassword(passwordEncoder.encode(requestAdmin.getPassword()));
        userRepository.save(entity);
        return new Response(200,entity.getName()+"'ning paroli muvaffaqiyatli o'zgartirildi");
    }
    public Response createUser(UserCreateDTO dto) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(dto.getUsername());
        if(byUsername.isPresent()){
            throw new BadRequestException("Ushbu username bilan tizimda foydalanuvchi mavjud");
        }
        Optional<UserEntity> byEmail = userRepository.findByEmail(dto.getEmail());
        if(byEmail.isPresent()){
            throw new BadRequestException("Ushbu email bilan tizimda foydalanuvchi mavjud");
        }
        if (!validationUtil.isValidEmail(dto.getEmail())) {
            throw new BadRequestException("Email pochta xato kiritilgan");
        }
        if(!dto.getPassword().equals(dto.getConfirmPsw())){
            throw new BadRequestException("Parollar mos emas.");
        }

        UserEntity userEntity = UserEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .isLocked(Boolean.FALSE)
                .failAttempt(0)
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole()==null?Role.MANAGER:dto.getRole())
                .deleted(Boolean.FALSE)
                .loginDate(LocalDateTime.now())
                .logoutDate(LocalDateTime.now())
                .build();
        var save = userRepository.save(userEntity);

        UserDTO userDTO = UserDTO.builder()
                .name(save.getName())
                .email(save.getEmail())
                .role(save.getRole())
                .build();
        return new Response(201,"User was successfully created",userDTO);
    }

    public List<UserDTO> getUsers() {
        UserEntity currentUser = getCurrentUser();
        Iterable<UserEntity> list = userRepository.findAllByDeletedFalse();
        List<UserDTO> dtoList= new LinkedList<>();
        if(currentUser.getRole().equals(Role.MANAGER)){
            UserDTO dto = new UserDTO();
            dto.setId(currentUser.getId());
            dto.setName(currentUser.getName());
            dto.setUsername(currentUser.getEmail());
            dto.setEmail(currentUser.getEmail());
            dto.setRole(currentUser.getRole());

            if(currentUser.getLogoutDate().isAfter(LocalDateTime.now())){
                dto.setOnline(Boolean.TRUE);
            }else {
                dto.setOnline(Boolean.FALSE);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                // Format the LocalDateTime object using the formatter
                String formattedDateTime = currentUser.getLogoutDate().format(formatter);

                dto.setLastOnlineDate(formattedDateTime);
            }
            dto.setCreatedDate(currentUser.getCreatedDate());
            dtoList.add(dto);
            return dtoList;

        }
        list.forEach(user -> {

            if(currentUser.getRole().equals(Role.ADMIN)&&!user.getEmail().equals(currentUser.getEmail())){

                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());

                    if(user.getLogoutDate().isAfter(LocalDateTime.now())){
                        dto.setOnline(Boolean.TRUE);
                    }else {
                        dto.setOnline(Boolean.FALSE);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                        // Format the LocalDateTime object using the formatter
                        String formattedDateTime = user.getLogoutDate().format(formatter);

                        dto.setLastOnlineDate(formattedDateTime);
                    }
                    dto.setCreatedDate(user.getCreatedDate());
                    dtoList.add(dto);

            }
        });

        return dtoList;
    }
    public UserEntity get(Integer id){

        Optional<UserEntity> byId = userRepository.findById(id);
        if(byId.isEmpty()){
            throw new NotFoundException("User could not be found by this id: "+id);
        }
        return byId.get();
    }
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (UserEntity) authentication.getPrincipal();
    }
    public UserDTO getUserDTO(){
        UserEntity entity = getCurrentUser();
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        return dto;


    }

    public Response delete(Integer id) {
        UserEntity entity = get(id);
        List<Token> byUserId = tokenRepository.findByUserEntityId(id);
        if(!byUserId.isEmpty()){
            tokenRepository.deleteAll(byUserId);
        }
        entity.setDeleted(Boolean.TRUE);
        userRepository.save(entity);
        return new Response(200,"User was successfully deleted");
    }
    public Response update(UserUpdateDTO dto){
        Optional<UserEntity> byUsername = userRepository.findByUsername(dto.getUsername());

        if (byUsername.isPresent()) {
            if(!byUsername.get().getId().equals(dto.getId())){
                throw new BadRequestException("Ushbu username bilan tizimda foydalanuvchi mavjud");
            }
        }
        Optional<UserEntity> byEmail = userRepository.findByEmail(dto.getEmail());
        if(byEmail.isPresent()){
            if(!byEmail.get().getId().equals(dto.getId())){
                throw new BadRequestException("Ushbu email bilan tizimda foydalanuvchi mavjud");
            }
        }
        if (!validationUtil.isValidEmail(dto.getEmail())) {
            throw new BadRequestException("Email pochta xato kiritilgan");
        }
        UserEntity entity = get(dto.getId());
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        userRepository.save(entity);
        return new Response(200,"Foydalanuvchi ma'lumotlari o'zgartirildi",dto);
    }


    public UserEntity getByUsername(String username) {
        Optional<UserEntity> byUsernameAndDeletedFalse = userRepository.findByUsernameAndDeletedFalse(username);
        if(byUsernameAndDeletedFalse.isEmpty()){
            throw new NotFoundException("User topilmadi: "+username);
        }
        return byUsernameAndDeletedFalse.get();


    }
}
