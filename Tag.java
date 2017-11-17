
public class Tag {
	public String OpenTag(String nameTag) {
		return "<" + nameTag + ">";
	}

	public String OpenTag(String nameTag, String property) {
		return "<" + nameTag + " " + property + ">";
	}

	public String CloseTag(String nameTag) {
		return "</" + nameTag + ">";
	}
}
