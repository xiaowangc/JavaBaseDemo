package com.chige.optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.net.ssl.HostnameVerifier;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author wangyc
 * @date 2021/6/8 12:51
 */
public class OptionalTest {

    public static void map() {
        Integer i = null;

        Optional<Integer> key = Optional.ofNullable(i);
        if (key.isPresent()) {
            System.out.println(key.get());
        }else {
            System.out.println("the value is null");
        }
        System.out.println("===2====");
        Integer the_value_is_null = key.orElseGet(() -> {
            System.out.println("the value is null");
            return 0;
        });
        System.out.println("the value is " + the_value_is_null);

        System.out.println("====3===");
        Integer integer = key.orElse(3);
        System.out.println("the value is " + integer);
    }

    /** 需求1： 获取医院对象里面50岁以上的医生
     *  如果存在一个大对象，里面又有小对象，最终需要取小对象里面的值，但是前提是需要这些对象非NULL
     */
    public void test2() {
        // 方式一：
        this.wayOne();
        // 方式二：
        this.wayTwo();
        // 方式三：
        this.wayThree();
    }
    /** 解决方式一：原始方式 - 判断 + for循环 缺点：代码量大 */
    private void wayOne() {
        long start = System.currentTimeMillis();
        List<Doctor> result = new ArrayList<>();
        Hospital hospital = this.getHospital();
        if (hospital != null) {
            List<Department> departmentList = hospital.getDepartmentList();
            if (!departmentList.isEmpty()) {
                for (Department department : departmentList) {
                    List<Doctor> doctorList = department.getDoctorList();
                    if (!doctorList.isEmpty()) {
                        for (Doctor doctor : doctorList) {
                            if (doctor.getAge() > 50) {
                                result.add(doctor);
                            }
                        }
                    }
                }
                result.forEach(System.out::println);
            }
        }
        System.out.println("way 1 use time: " + (System.currentTimeMillis() - start));

    }
    /** 解决方式二：利用Stream + Optional */
    private void wayTwo() {
        Long start = System.currentTimeMillis();
        Optional<Hospital> hospitalOpt = Optional.ofNullable(this.getHospital());
        if (hospitalOpt.isPresent()) {
            Hospital hospital = hospitalOpt.get();
            Optional<List<Department>> departmentList = Optional.ofNullable(hospital.getDepartmentList());
            if (departmentList.isPresent()) {
                List<Department> departments = departmentList.get();
                List<Doctor> result = departments.stream()
                        .map(Department::getDoctorList)
                        .filter(Objects::nonNull)
                        .flatMap(List::stream)
                        .filter(doctor -> doctor.getAge() > 50)
                        .collect(Collectors.toList());
                result.forEach(System.out::println);
            }
        }
        System.out.println("way 2 use time: " + (System.currentTimeMillis() - start));

    }
    /** 解决方式二：利用 判断 + Stream */
    private void wayThree() {
        Long start = System.currentTimeMillis();
        Hospital hospital =  this.getHospital();
        if (hospital != null) {
            List<Department> departmentList = hospital.getDepartmentList();
            if (!departmentList.isEmpty()) {
                List<Doctor> collect = departmentList.stream().map(Department::getDoctorList)
                        .filter(Objects::nonNull)
                        .flatMap(List::stream)
                        .filter(doctor -> doctor.getAge() > 50)
                        .collect(Collectors.toList());
                collect.forEach(System.out::println);
            }
        }
        System.out.println("way 3 use time: " + (System.currentTimeMillis() - start));
    }
    private Hospital getHospital() {
        Doctor doctor = new Doctor(1, 51, "Dor.A");
        Doctor doctor1 = new Doctor(2, 45, "Dor.B");
        Doctor doctor2 = new Doctor(3, 67, "Dor.C");
        Doctor doctor3 = new Doctor(4, 54, "Dor.D");
        Doctor doctor4 = new Doctor(5, 34, "Dor.E");
        Doctor doctor5 = new Doctor(6, 43, "Dor.F");
        List<Doctor> doctorList = Arrays.asList(doctor, doctor1, doctor2);
        Department department = new Department(doctorList,1,"Dep.A");
        Department department1 = new Department(Arrays.asList(doctor3, doctor4,doctor5),2,"Dep.B");
        Hospital hospital = new Hospital("Hos.A",Arrays.asList(department,department1));
        return hospital;
    }

    public static void main(String[] args) {
        OptionalTest test = new OptionalTest();
        test.test2();
        test.wayThree();
        test.wayTwo();
        test.wayOne();
        Optional<String> s = Optional.ofNullable("1");
        Object function = Function.identity().apply("r4");
        s.orElse("2");
        s.orElseGet(() -> {
            return "3";
        });
        boolean present = s.isPresent();

    }
    /** 医院 */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Hospital {
        private String name;
        private List<Department> departmentList;
    }
    /** 科室 */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Department {
        private List<Doctor> doctorList;
        private Integer depId;
        private String depName;
    }
    /** 医生*/
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public class Doctor {
        private Integer doctorId;
        private Integer age;
        private String name;
    }
}
