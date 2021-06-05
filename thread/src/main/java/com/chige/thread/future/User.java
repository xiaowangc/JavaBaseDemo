package com.chige.thread.future;

public class User {
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "age:" + this.age + "岁";
    }
    public static String handleContent(String tplContent, String oldLabel, String newLabel) {
        int indexOf = tplContent.indexOf(oldLabel);
        while (indexOf != -1) {
            if (indexOf != 0 && tplContent.charAt(indexOf - 1) == '{' && tplContent.charAt(indexOf + oldLabel.length()) == '}') {
                StringBuilder sb = new StringBuilder();
                sb.append(tplContent.substring(0,indexOf));
                String substring = tplContent.substring(indexOf, indexOf + oldLabel.length());
                if (!substring.equals(oldLabel)) {
                    System.out.println("error");
                    return tplContent;
                }
                sb.append(newLabel);
                String other = tplContent.substring(indexOf + oldLabel.length());
                sb.append(other);
                tplContent = sb.toString();
                indexOf = tplContent.indexOf(oldLabel);
            }
        }
        return tplContent;
    }
    public static void main(String[] args) {
        String content = "{ER}AGEGESFSFCEGS{GA}{ER}";
        String oldLabel = "ER";
        String newLabel = "GV";
        String s = handleContent(content,oldLabel, newLabel);
        System.out.println(s);
    }
}
