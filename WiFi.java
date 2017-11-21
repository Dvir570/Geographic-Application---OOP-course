import java.text.SimpleDateFormat;
/**
 *present a wifi we found.
 */
public class WiFi {
	private String type, mac, ssid, freq, lat, lon, alt, signal, model, time;

	public WiFi() {
	}

	public WiFi(String line, String model) {
		String[] temp = line.split(",");
		this.model = model;
		this.type = temp[10];
		this.mac = temp[0];
		this.ssid = temp[1];
		this.time = temp[3];
		this.lat = temp[6];
		this.lon = temp[7];
		this.alt = temp[8];
		this.signal = temp[5];

		int channel = Integer.parseInt(temp[4]);
		int i;
		if (channel >= 1 && channel <= 14) {
			i = (channel - 1) * 5 + 2412;
			this.freq = i + "";
		} else {
			if (channel >= 36 && channel <= 165) {
				i = (channel - 34) * 5 + 5170;
				this.freq = i + "";
			} else
				this.freq = "";
		}
	}

	/**
	 * @return lat
	 */
	public String getLat() {
		return this.lat;
	}

	/**
	 * @return lon
	 */
	public String getLon() {
		return this.lon;
	}

	/**
	 * @return alt
	 */
	public String getAlt() {
		return this.alt;
	}

	/**
	 * @return model (ID of device)
	 */
	public String getModel() {
		return this.model;
	}

	/**
	 * @return type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @return mac
	 */
	public String getMac() {
		return this.mac;
	}

	/**
	 * @return SSID
	 */
	public String getSSID() {
		return this.ssid;
	}

	/**
	 * @return freq
	 */
	public String getFreq() {
		return this.freq;
	}

	/**
	 * @return time
	 */
	public String getTime() {
		return this.time;
	}

	/**
	 * @return signal
	 */
	public String getSignal() {
		return this.signal;
	}

	public String toString() {
		return model + "," + time + "," + lat + "," + lon + "," + alt + "," + mac + "," + ssid + "," + freq + ","
				+ signal;
	}
}
