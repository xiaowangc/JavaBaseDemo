package com.chige.guavaApi.map;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Table - 双键Map
 * 特点：允许一个value存在两个key
 *
 * @author wangyc
 * @date 2022/3/17
 */
public class TableMap {
    //场景举例：记录员工每个月工作的天数。用java中普通的Map实现的话就需要两层嵌套：
    public void jdkMap() {
        Map<String, Map<String, Integer>> map = new HashMap<>();
        //存放元素
        Map<String, Integer> workMap = new HashMap<>();
        workMap.put("Jan", 20);
        workMap.put("Feb", 28);
        map.put("Hydra", workMap);

        //取出元素
        Integer dayCount = map.get("Hydra").get("Jan");
    }

    /**
     * 使用guava的双键map来实现上面的场景
     */
    public static void guavaTableMap() {
        // 可以理解Table对象是一张表格的映射
        // 0、构建双键Map对象
        Table<String,String,Integer> table = HashBasedTable.create();
        // 1、存值
        table.put("Hydra", "Jan", 20);
        table.put("Hydra", "Feb", 28);

        table.put("Trunks", "Jan", 28);
        table.put("Trunks", "Feb", 16);

        // 2、取出元素
        Integer i1 = table.get("Hydra", "Jan");
        Integer i2 = table.get("Hydra", "Feb");
        Integer i3 = table.get("Trunks", "Feb");
        System.out.println("i1 = " + i1 + ",i2 = " + i2 + ",i3 = " + i3);

        // 3、获取key或者value的集合
        Set<String> rowKeySet = table.rowKeySet(); // 获取所有的行key的集合
        Set<String> columnKeySet = table.columnKeySet(); //获取所有的列key集合
        Collection<Integer> values = table.values(); // 读取所有的value值,会出现重复值

        Map<String, Integer> columnMap = table.row("Hydra"); // 获取指定行的所有列名称与列值的映射map
        Map<String, Integer> rowMap = table.column("Jan"); //获取指定列的所有行名称与行值的映射map
        Set<Table.Cell<String, String, Integer>> cells = table.cellSet();
        cells.forEach(cell -> System.out.println("rowKey=" + cell.getRowKey() + ",columnKey=" + cell.getColumnKey() + ",value=" + cell.getValue()));

        /** 小总结：获取key的集合是不包含重复元素的，而value集合则包含了所有元素*/

        // 4、转换rowKey和columnKey，行列转置
        Table<String, String, Integer> transpose = Tables.transpose(table); //调用转置方法
        Map<String, Map<String, Integer>> transposeRowMap = transpose.rowMap(); //转置后的rowMap相当于转置前的columnMap
        Map<String, Map<String, Integer>> transposeColumnMap = transpose.columnMap();

        // 5、转为双层Map
        Map<String, Map<String, Integer>> rowMapMap = table.rowMap(); // 转换成双层map（先取行值，再取列值）
        Map<String, Map<String, Integer>> stringMapMap1 = table.columnMap(); //转换为双层map（先取列值，再取行值）

    }

    public static void main(String[] args) {
        guavaTableMap();
    }


}
