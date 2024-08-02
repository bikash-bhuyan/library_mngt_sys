package ai.javis.project.library_management_system.Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Utils {
    public static String dateFormatter(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}
