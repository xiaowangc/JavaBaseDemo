package com.chige.utils.json;



/**
 * @author wangyc
 * @date 2023/4/19
 */

public class JsonTest {

    public static void main(String[] args) {
        User user = new User();
        user.setUserName("用户名");
        user.setUserAge("19");
        user.setA("a");
        Company company = new Company();
        company.setCompanyName("公司名");
        company.setCompanyNo("公司号");
        UserRequest userRequest = new UserRequest();
        userRequest.setUserType("03");
        userRequest.setUserIdType("003");
        userRequest.setUser(user);
        userRequest.setCompany(company);
        String userStr = JsonUtils.toJSONString(user);
        System.out.println(userStr);
        String companyStr = JsonUtils.toJSONString(company);
        System.out.println(companyStr);
        String requestStr = JsonUtils.toJSONString(userRequest);
        System.out.println(requestStr);
        UserRequest userRequest1 = JsonUtils.parseObject(requestStr, UserRequest.class);
        System.out.println(userRequest1.getUser());
        System.out.println(userRequest1.getCompany());

    }

}
