package kr.or.ddit.com.report.service;

public interface ReportService {

	/**
	 * @param reportVO
	 * @return
	 */
	ReportVO selectReport(ReportVO reportVO);

	/**
	 *
	 * @param reportVO
	 * @return
	 */
	boolean insertReport(ReportVO reportVO);

}
