package homework.three;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class OperationCalendar{
	public static int getYearsBetweenDates(GregorianCalendar dt1, GregorianCalendar dt2){

		if(dt1.compareTo(dt2) == -1){
			return 0;
		}
		
		GregorianCalendar dt3 = (GregorianCalendar) Calendar.getInstance();
		dt3 = (GregorianCalendar) dt1.clone();		
		int years = 0;
/*		
		years = (dt1.get(Calendar.YEAR) - dt2.get(Calendar.YEAR));
		if(dt1.get(Calendar.MONTH) == dt2.get(Calendar.MONTH)){
			if(dt1.get(Calendar.DAY_OF_MONTH) < dt2.get(Calendar.DAY_OF_MONTH)){
				years--;
			}
		}
		else if (dt1.get(Calendar.MONTH) < dt2.get(Calendar.MONTH)){
			years--;
		}
		years = 0;
*/		
		dt3.add(Calendar.YEAR, -1 * dt2.get(Calendar.YEAR));
		dt3.add(Calendar.MONTH, -1 * dt2.get(Calendar.MONTH));
		dt3.add(Calendar.DAY_OF_MONTH, -1 * dt2.get(Calendar.DAY_OF_MONTH));
		
		years = dt3.get(Calendar.YEAR);
		
		return years;
	}

}
