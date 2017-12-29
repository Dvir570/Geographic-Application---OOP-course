package src;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AvgSamplePoint {
	private double avgLon=0;
	private double avgLat=0;
	private double avgAlt=0;
	private ArrayList<Data> datas;
	private ArrayList<WiFi> input;
	public AvgSamplePoint(ArrayList<Data> datas, int numOfDatas, ArrayList<WiFi> input) {
		this.input = input;
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
			sums[0]+=(Double.parseDouble(datas.get(i).getWifis().get(0).getLat())*(datas.get(i).getPi()));
			sums[1]+=(Double.parseDouble(datas.get(i).getWifis().get(0).getLon())*(datas.get(i).getPi()));
			sums[2]+=(Double.parseDouble(datas.get(i).getWifis().get(0).getAlt())*(datas.get(i).getPi()));
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

	@Override
	public String toString() {
		String s="";
		WiFi w = input.get(0);
		s+= w.getTime()+","+w.getModel()+","+avgLon+","+avgLat+","+avgAlt+input.size();
		for(int i=0;i<input.size();i++) {
			s+=","+input.get(i).getMac()+","+input.get(i).getSSID()+","+input.get(i).getFreq()+","+input.get(i).getSignal();
		}
		return s;
	}
	
	
}
