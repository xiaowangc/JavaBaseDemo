import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author wangyc
 * @date 2022/9/26
 */
public class ConfigCreate {

    private void personRelation() {
        int startId = 141157616;
//        int endId = 590435021;
        int endId = 140157616;
        int size = 5000000;
//        int startId = 846409;
//        int endId = 22141917;
//        int size = 1;
        String space4 = "    ";
        String space8 = space4 + space4;
        String space10 = space8 + "  ";
        for (int i = startId; i <= endId; i += size) {
            StringBuilder sb = new StringBuilder();
            sb.append(space4 + "{\n" + space8 + "name: follow\n");
            sb.append(space8 + "type: {\n");
            sb.append(space10 + "source: mysql\n");
            sb.append(space10 + "sink: client\n");
            sb.append(space8 + "}\n");
            sb.append(space8 + "host:10.1.2.200\n");
            sb.append(space8 + "port:4000\n");
            sb.append(space8 + "database:\"person_relation\"\n");
            sb.append(space8 + "table:\"person_relation\"\n");
            sb.append(space8 + "user:\"yanfa\"\n");
            sb.append(space8 + "password:\"yanfa!@#\"\n");

            sb.append(space8 + "sentence:\"SELECT CONCAT_WS('_',id_type,person_id) as VID,CONCAT_WS('_',link_id_type,link_person_id) as linkVID,create_time,upd_time,remark_name from person_relation where id BETWEEN ");
            sb.append(i + " and " + (i + size - 1) + " and relation_type = 1 and state = 1\"\n");
            sb.append(space8 + "fields: [create_time,upd_time,remark_name]\n");
            sb.append(space8 + "nebula.fields: [create_time,upd_time,remark_name]\n");
            sb.append(space8 + "source: {\n" + space10 + "field: VID\n" + space8 + "}\n");
            sb.append(space8 + "target: {\n" + space10 + "field: linkVID\n" + space8 + "}\n");
            sb.append(space8 + "batch: 5000\n");
            sb.append(space8 + "partition: 10\n");
            sb.append(space4 + "}\n");
            System.out.println(sb);
        }
    }


    /**
     * 从mysql同步数据至图数据库
     *
     * @param tagName   图数据库点tag名称
     * @param fieldName mysql表字段名
     */
    public static void docPatient(String tagName, String fieldName) {
        int startId = 1;
        int endId = 466426;
//        int endId = 30;
        int size = 100000;
        String space4 = "    ";
        String space8 = space4 + space4;
        String space10 = space8 + "  ";
        for (int i = startId; i <= endId; i += size) {
            StringBuilder sb = new StringBuilder();
            sb.append(space4 + "{\n" + space8 + "name: " + tagName + "\n");
            sb.append(space8 + "type: {\n");
            sb.append(space10 + "source: mysql\n");
            sb.append(space10 + "sink: client\n");
            sb.append(space8 + "}\n");
            sb.append(space8 + "host:10.1.2.55\n");
            sb.append(space8 + "port:3306\n");
            sb.append(space8 + "database:\"doctor_app\"\n");
            sb.append(space8 + "table:\"doc_patient\"\n");
            sb.append(space8 + "user:\"yanfa\"\n");
            sb.append(space8 + "password:\"yanfa!@#\"\n");

            sb.append(space8 + "sentence:\"SELECT CONCAT_WS('_',3,doctor_id) as VID,CONCAT_WS('_',6,member_id) as linkVID, now() as create_time,now() as upd_time, ");
            sb.append(fieldName + " as num FROM doc_patient WHERE " + fieldName + " > 0 and patient_id BETWEEN ");
            sb.append(i + " and " + (i + size - 1) + "\"\n");
            sb.append(space8 + "fields: [create_time,upd_time,num]\n");
            sb.append(space8 + "nebula.fields: [create_time,upd_time,num]\n");
            sb.append(space8 + "source: {\n" + space10 + "field: VID\n" + space8 + "}\n");
            sb.append(space8 + "target: {\n" + space10 + "field: linkVID\n" + space8 + "}\n");
            sb.append(space8 + "batch: 5000\n");
            sb.append(space8 + "partition: 10\n");
            sb.append(space4 + "}\n");
            System.out.println(sb);
        }
    }

    public static String arrayUpdateScript(String arrayName, String fieldName, String paramName, Integer updateType) {
        String addScript = String.format("for(e in ctx_source.%s){if(e.teamId == params.teamId){if(e.%s == null){e.%s = [params.%s];}else{e.%s.add(params.%s);}}}",
                arrayName, fieldName, fieldName, paramName, fieldName, paramName);
        String removeScript = String.format("for(e in in ctx_source.%s){if(e.teamId == params.teamId){if(e.%s != null){for(int i = 0; i < e.%s.size(); i++){if(e.%s[i] == params.%s){e.%s.remove(i);break;}}}}}",
                arrayName, fieldName, fieldName, fieldName, paramName, fieldName);


            return updateType == 1 ? addScript : removeScript;
        }

        public static void main (String[]args){
            String s = arrayUpdateScript("crmTeamList", "expandMobileList", "mobile", 2);
            System.out.println(s);
        }

        private static Map<Character, Character> initMap = new HashMap<>();

        static {
            initMap.put('{', '}');
            initMap.put('(', ')');
            initMap.put('[', ']');
        }

        public static boolean isValid (String s){
            char[] charArray = s.toCharArray();
            LinkedList<Character> stack = new LinkedList<>();

            for (char c : charArray) {
                if (initMap.containsKey(c)) {
                    stack.addLast(c);
                } else if (initMap.get(stack.removeLast()) != c) {
                    return false;
                }
            }
            return stack.isEmpty();
        }

        static class Advert {
            private String last_ad_target_url;
            private String last_click_ad_time;
            private String last_ad_id;
            private String last_ad_name;
            private String last_ad_promoting_items;
            private String last_location_id;
            private String last_ad_img_url;
        }
    }
