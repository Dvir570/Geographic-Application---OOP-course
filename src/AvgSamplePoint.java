import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AvgSamplePoint {
	private double avgLon=0;
	private double avgLat=0;
	private double avgAlt=0;
	private ArrayList<Data> datas;

	public AvgSamplePoint(ArrayList<Data> datas, int numOfDatas) {
		this.datas = datas;
		Collections.sort(this.datas, new Comparator<Data>() {

			@Override
			public int compare(Data d1, Data d2) {
				if (d2.getPi() > d1.getPi())
					return 1;
				else if (d2.getPi() < d1.getPi())
					return -1;
				return 0;
			}
		});
		if (numOfDatas<datas.size())
			datas.removeAll(datas.subList(numOfDatas-1, datas.size()));
		double sums[] = new double[3];
		double sum_wights=0;
		for (int i=0;i<datas.size();i++) {
			sums[0]+=(Double.parseDouble(datas.get(i).getWifis().get(i).getLat())*(datas.get(i).getPi()));
			sums[1]+=(Double.parseDouble(datas.get(i).getWifis().get(i).getLon())*(datas.get(i).getPi()));
			sums[2]+=(Double.parseDouble(datas.get(i).getWifis().get(i).getAlt())*(datas.get(i).getPi()));
			sum_wights+=datas.get(i).getPi();
		}
		this.avgLat=sums[0]/sum_wights;
		this.avgLon=sums[1]/sum_wights;
		this.avgAlt=sums[2]/sum_wights;
	}

	public double getAvgLon() {
		return avgLon;
	}

	public double getAvgLat() {
		return avgLat;
	}

	public double getAvgAlt() {
		return avgAlt;
	}
	
	
}
