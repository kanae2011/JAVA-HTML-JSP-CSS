package notice;

public class NoticeVO {

	//변수 선언 
	private long no; 
	private String title;
	private String content;
	private String startDate; 
	private String endDate;
	private String writeDate;
	private String updateDate;
	
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	//데이터 확인용 
	@Override
	public String toString() {
		return "NoticeVO [no=" + no + ", title=" + title + ", content=" + content + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", writeDate=" + writeDate + ", updateDate=" + updateDate + "]";
	}
	
	
	
	
}
