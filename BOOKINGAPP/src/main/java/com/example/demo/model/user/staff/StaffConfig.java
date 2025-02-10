// package com.example.demo.model.user.staff;

// import java.util.List;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.example.demo.repository.StaffRepository;

// @Configuration
// public class StaffConfig {

//     @Bean
//     CommandLineRunner commandLineRunner(StaffRepository staffRepository){
//         return args -> {
            
//             Staff staff1 = new Staff("Huu Phong","phong@gmail.com","phong123","phong123","0989612290",Long.parseLong("1"));
//             staffRepository.saveAll(
//                 List.of(staff1));
//         };
//     }
// }
