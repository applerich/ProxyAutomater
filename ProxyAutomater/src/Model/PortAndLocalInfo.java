package Model;

import java.util.List;

public class PortAndLocalInfo {
private List<String> list;
private String port;
	public void saveList(List<String> list) {
		this.list = list;
	}
	public List<String> getList() {
		return list;
	}
	public void savePort(String string) {
		this.port = string;
	}
	public String getPort() {
		return port;
	}
}
