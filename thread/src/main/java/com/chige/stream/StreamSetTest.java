package com.chige.stream;

import lombok.Data;

import java.util.*;

/**
 * @author wangyc
 * @date 2021/7/24
 */
public class StreamSetTest {

    private boolean a;

    /**
     *  找出set集合中的每个元素属性的最大值
     */
    public void max() {
        List<DoctorDTO> personList= new ArrayList<>();
        DoctorDTO d1 = new DoctorDTO("123",3);
        DoctorDTO d2 = new DoctorDTO("123",54);
        DoctorDTO d3 = new DoctorDTO("123",5);
        DoctorDTO d4 = new DoctorDTO("123",23);
        DoctorDTO d5 = new DoctorDTO("32",45);
        DoctorDTO d6 = new DoctorDTO("12",34);
        Map<String,Double> map = new HashMap<>();
        map.put("123",1.0);
        map.put("123",534.0);
        map.put("343",34.0);
        map.put("12",234.0);
        map.put("123",2.0);
        map.put("123",54.0);
        Set<DoctorDTO> personSet = new HashSet<>();
        personSet.add(d1);
        personSet.add(d2);
        personSet.add(d3);
        personSet.add(d4);
        personSet.add(d5);
        personSet.add(d6);
        personSet.add(new DoctorDTO("123",3));
        personSet.add(new DoctorDTO("123w12",5));
        personSet.add(new DoctorDTO("454",7));
        personSet.add(new DoctorDTO("13",8));
        DoctorDTO max = personSet.stream().max(Comparator.comparingInt(DoctorDTO::getId)).get();
        System.out.println("a="+max.getLinkPersonId() + "=== id=" + max.getId());
    }


    public static void main(String[] args) {
        StreamSetTest test = new StreamSetTest();
        test.max();
    }
    @Data
    public class DoctorDTO{
        private Integer id;
        private String linkPersonId;
        public DoctorDTO(String linkPersonId, Integer id) {
            this.linkPersonId = linkPersonId;
            this.id = id;
        }
        public DoctorDTO(){}
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || !(obj instanceof DoctorDTO)) {
                return false;
            }

            DoctorDTO doctorDTO = (DoctorDTO) obj;
            if (Objects.equals(doctorDTO.getId(), this.id) &&
                    Objects.equals(doctorDTO.getLinkPersonId(), this.linkPersonId)) {
                return true;
            }
            return false;

        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + (id == null ? 0 : id.hashCode());
            result = 31 * result + (linkPersonId == null ? 0 : linkPersonId.hashCode());
            return result;
        }
    }
}
