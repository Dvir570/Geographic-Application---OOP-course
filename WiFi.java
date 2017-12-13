
/**
 * present a wifi we found.
 */
public class WiFi {
	private String type, mac, ssid, freq, lat, lon, alt, signal, model, time;

	public WiFi() {
	}

	/**
	 * @param line
	 *            represent the whole information of one line in the CSV file
	 * @param model
	 *            device id
	 */
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

	public WiFi(String model, String mac, String ssid, String freq, String lat, String lon, String alt, String signal,
			String time) {
		this.model = model;
		this.freq = freq;
		this.mac = mac;
		this.ssid = ssid;
		this.time = time;
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
		this.signal = signal;
	}

	public WiFi(String model, String mac, String ssid, String freq, String signal, String time) {
		this.model = model;
		this.freq = freq;
		this.mac = mac;
		this.ssid = ssid;
		this.time = time;
		this.signal = signal;
	}

	/**
	 * @return latitude
	 */
	public String getLat() {
		return this.lat;
	}

	/**
	 * @return longtitude
	 */
	public String getLon() {
		return this.lon;
	}

	/**
	 * @return altitude
	 */
	public String getAlt() {
		return this.alt;
	}

	/**
	 * @return model device id
	 */
	public String getModel() {
		return this.model;
	}

	/**
	 * @return type of the net (wifi, bt ext )
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
	 * @return frequency
	 */
	public String getFreq() {
		return this.freq;
	}

	/**
	 * @return date time
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

	public void setSignal(String signal) {
		this.signal = signal;
	}

}
