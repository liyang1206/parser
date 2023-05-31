
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
//import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;


public class ExportDemo {


    public static List<Map<Integer,Double>> get_exportdata_list(String sheet, ArrayList<double[]> data2)
    {
        List<Map<Integer,Double>> list = new LinkedList<>();


        for(int i=0;i<data2.toArray().length;i++)
        {
            Map<Integer, Double> map = new HashedMap<>() ;
            for(int j=0;j<data2.get(0).length;j++)
            {
                map.put(j, data2.get(i)[j]);
            }
            list.add(map);
        }
        return list;
    }
}