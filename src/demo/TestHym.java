package demo;

import net.keepsoft.hydrology.model.nativelib.HydroModelJni;

public class TestHym {

	public static void main(String[] args) {
		String path = "F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/grplat1/FORE_MODEL/x64/grdhm.dll";
		HydroModelJni hims = new HydroModelJni(path);
		System.out.println(hims);
		String indexPath = "F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/grplat1/FORE_MODEL/2006051810/task.txt";
		hims.calculate(indexPath);
	}
}
