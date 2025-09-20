package com.lifespring.config;


import com.lifespring.dto.AppointmentDto;
import com.lifespring.model.Appointment;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();


        /**
          Here my mapper is through ambiguity because it got confused between doctorName and hospitalName.
         modelMapper still sees it in the entity (appointment.getDoctor().getHospitalName()) and gets confused,
         because it tries to be smart and match doctorName with any *Name field it can find under Doctor.
         but I don't want to send the hospitalName , only want to send the doctorName
         To achieve this i used STRICT mapping , If you want ModelMapper to only match exact property names
         (instead of guessing with partial matches), configure it like this:
         With STRICT, modelMapper won’t confuse doctorName with hospitalName.
        **/

        // set the matching strategy globally
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Custom modelMapper of Appointments
        // Here we are mapping our own field mapping

        modelMapper.typeMap(Appointment.class, AppointmentDto.class)
                .addMappings(m->{
                    // Mapping the doctor fields first
                    m.map(src->src.getDoctor().getDoctorId(),AppointmentDto::setDoctorId);
                    m.map(src -> src.getDoctor().getDoctorName() , AppointmentDto :: setDoctorName );
                    m.map(src -> src.getDoctor().getEmail(),AppointmentDto::setDoctorEmail);
                    m.map(src-> src.getDoctor().getPhoneNumber(), AppointmentDto::setDoctorPhone);
                    m.map(src-> src.getDoctor().getSpecialization(),AppointmentDto::setDoctorSpecialization);

                    // Mapping the patient fileds

                    m.map(src-> src.getPatient().getPatientId(),AppointmentDto::setPatientId);
                    m.map(src->src.getPatient().getPatientName(),AppointmentDto::setPatientName);
                    m.map(src-> src.getPatient().getPhoneNumber(),AppointmentDto::setPatientPhone);

                    //other field mapping
                    m.map(Appointment::getAppointmentDateTime,AppointmentDto::setAppointmentDateTime);
                    m.map(Appointment::getStatus,AppointmentDto::setStatus);


                });


        return modelMapper;
    }
}
