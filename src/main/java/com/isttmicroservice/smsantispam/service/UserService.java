package com.isttmicroservice.smsantispam.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isttmicroservice.smsantispam.dto.EmailDTO;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.dto.UserDTO;
import com.isttmicroservice.smsantispam.entity.Role;
import com.isttmicroservice.smsantispam.entity.User;
import com.isttmicroservice.smsantispam.repository.RoleRepo;
import com.isttmicroservice.smsantispam.repository.UserRepo;

public interface UserService {
    void create(UserDTO userDTO);

    UserDTO getUserByUsername(String username);

	void update(UserDTO userDTO);
	
	void updatePassword(UserDTO userDTO);
	
	void forgetPassword(UserDTO userDTO, EmailDTO emailDTO);

    void delete(Integer id);

    void deleteAll(List<Integer> ids);

    UserDTO get(Integer id);
    
    UserDTO findByUsername(String username);

    ResponseDTO<List<UserDTO>> find(SearchDTO searchDTO);
}

@Service
class UserServiceImpl implements  UserService{


    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;
    
	@Autowired
	private EmailService emailService;

    @Transactional
    @Override
    public void create(UserDTO userDTO) {

    	  User user = new User();
          user.setName(userDTO.getName());
          user.setUsername(userDTO.getUsername());
          user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));  
          user.setAddress(userDTO.getAddress());
          user.setPhone(userDTO.getPhone());
          user.setEmail(userDTO.getEmail());
          System.out.println(userDTO.getName() + " " + userDTO.getUsername());
            	
     		 Role role = new Role();
     		 role.setRole("ROLE_MEMBER") ;
     		 role.setUser(user);
    		 
     		 roleRepo.save(role);  
          userRepo.save(user);
          

          userDTO.setId(user.getId());
    }

    @Transactional
    @Override
    public void update(UserDTO userDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(UserDTO.class, User.class)
                .setProvider(p -> userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new));

        User user = mapper.map(userDTO, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepo.save(user);
//        userDTO.setId(user.getId());
    }
	@Override
	@Transactional
	public void updatePassword(UserDTO userDTO){
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));  
        System.out.println(userDTO.getPassword());
		userRepo.save(user);
	}
	@Override
	@Transactional
	public void forgetPassword(UserDTO userDTO, EmailDTO emailDTO){
		User user = userRepo.findByUsernameForgotPassword(userDTO.getUsername()).orElseThrow(NoResultException::new);
		System.out.println(user.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));  
        emailService.sendEmail(userDTO, emailDTO);
		userRepo.save(user);
	}

    @Transactional
    @Override
    public void delete(Integer id) {
        User user = userRepo.findById(id).orElseThrow(NoResultException::new);
        if(user!= null) {
        	if(user.getRole()!= null) {
        		roleRepo.deleteByUserId(id);	
        	}
            userRepo.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void deleteAll(List<Integer> ids) {
        userRepo.deleteAllByIdInBatch(ids);
    }

    @Override
    public UserDTO get(Integer id) {
        return userRepo.findById(id).map(user -> convert(user)).orElseThrow(NoResultException::new);
    }
    
    @Override
	public UserDTO getUserByUsername(String username) {
		UserDTO userDTO = userRepo.findByUsername(username).map(user -> convert(user)).orElseThrow(NoResultException::new);
		return userDTO;
	}
    
    @Override
    public UserDTO findByUsername(String username) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        return userOptional.map(this::convert).orElse(null);
    }

    @Override
    public ResponseDTO<List<UserDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<User> page = userRepo.find(searchDTO.getValue(), pageable);

        ResponseDTO<List<UserDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(user -> convert(user)).collect(Collectors.toList()));
        return responseDTO;
    }

    private UserDTO convert(User user) {
        return new ModelMapper().map(user, UserDTO.class);
    }
}