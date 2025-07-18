package kr.or.ddit.util.pdf.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextFontResolver;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

import kr.or.ddit.util.pdf.service.HTMLtoPdfService;

@Service
public class HTMLtoPdfServiceImpl implements HTMLtoPdfService {

	private static final String DEFAULT_FONT_PATH = "C:/Windows/Fonts/malgun.ttf";
	private static final String MAC_FONT_PATH = "/System/Library/Fonts/AppleGothic.ttf";
	private static final String RESOURCE_FONT_PATH = "fonts/NanumGothic.ttf";

	@Override
	public byte[] convertHtmlToPdf(String htmlContent, String cssContent) throws DocumentException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			// HTML 내용 검증 및 전처리
			if (htmlContent == null || htmlContent.trim().isEmpty()) {
				throw new IllegalArgumentException("HTML 내용이 비어있습니다.");
			}

			// 완전한 HTML 문서로 변환
			String fullHtml = buildFullHtmlDocument(htmlContent, cssContent);
			System.out.println("==============fullHtml=================");		// 완성된 html 소스 확인
			System.out.println(fullHtml);
			System.out.println("=========================================");

			// Flying Saucer 렌더러 생성
			ITextRenderer renderer = new ITextRenderer();

			// 한글폰트 포함 및 폰트 설정
			setupKoreanFonts(renderer);

			// HTML 문자열 문서화
			renderer.setDocumentFromString(fullHtml);
			// 문서를 위치 줄바꿈 폰트 페이지분할 등 계산하여 적용
			renderer.layout();
			// PDF 생성
			renderer.createPDF(baos);

		} catch (Exception e) {
			System.err.println("PDF 생성 중 오류: " + e.getMessage());
			e.printStackTrace();
			throw new DocumentException("PDF 생성 실패: " + e.getMessage());
		}

		return baos.toByteArray();
	}

	@Override
	public String buildFullHtmlDocument(String htmlContent, String cssContent) {
	        
	        StringBuilder fullHtml = new StringBuilder();
	        fullHtml.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
	        fullHtml.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
	        fullHtml.append("<head>");
	        fullHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
	        fullHtml.append("<title>PDF Document</title>");
	        fullHtml.append("<style type=\"text/css\">");
	        fullHtml.append(getDefaultStyles());
	        if (cssContent != null && !cssContent.trim().isEmpty()) {
	            fullHtml.append(cssContent);
	        }
	        fullHtml.append("</style>");
	        fullHtml.append("</head>");
	        fullHtml.append("<body>");
	        fullHtml.append(htmlContent);
	        fullHtml.append("</body>");
	        fullHtml.append("</html>");
	        
	        return fullHtml.toString();
	}

	/**
	 * pdf만들때 추가할 html기본문서 css양식
	 */
	private String getDefaultStyles() {
        return """
                body { 
                    font-family: 'Malgun Gothic', 'Apple Gothic', 'NanumGothic', sans-serif; 
                    margin: 20px; 
                    line-height: 1.6;
                    color: #333;
                    font-size: 12px;
                }
                h1, h2, h3, h4, h5, h6 { 
                    color: #2c3e50; 
                    margin-top: 0;
                    margin-bottom: 10px;
                }
                h1 { font-size: 24px; }
                h2 { font-size: 20px; }
                h3 { font-size: 16px; }
                table { 
                    width: 100%; 
                    border-collapse: collapse; 
                    margin: 20px 0;
                    font-size: 11px;
                }
                th, td { 
                    border: 1px solid #ddd; 
                    padding: 8px; 
                    text-align: left; 
                }
                th { 
                    background-color: #f8f9fa; 
                    font-weight: bold; 
                }
                .header { 
                    text-align: center; 
                    margin-bottom: 30px; 
                }
                .footer { 
                    text-align: center; 
                    margin-top: 30px; 
                    font-size: 10px; 
                    color: #666; 
                }
                p { 
                    margin: 0 0 10px 0; 
                }
                .highlight { 
                    background-color: #fff3cd; 
                    padding: 5px; 
                }
                .text-center { text-align: center; }
                .text-right { text-align: right; }
                .bold { font-weight: bold; }
                """;
	}
	
   /**
     * 폰트설정 (한글 폰트 하나라도 있으면 한글 가능, 나머지 폰트 추가해서 커스텀 가능)
     */
    private void setupKoreanFonts(ITextRenderer renderer) {
        try {
            ITextFontResolver fontResolver = renderer.getFontResolver();

            // 폰트 경로들 목록 담을 리스트
            List<String> fontPaths = new ArrayList<>();

            // OS별 기본 폰트
            if (new File(DEFAULT_FONT_PATH).exists()) {
                fontPaths.add(DEFAULT_FONT_PATH); // 예: "C:/Windows/Fonts/malgun.ttf"
            }
            if (new File(MAC_FONT_PATH).exists()) {
                fontPaths.add(MAC_FONT_PATH); // 예: "/System/Library/Fonts/AppleGothic.ttf"
            }

            // 클래스패스 리소스 폰트들 추가
            // 혹시나 사용하고 싶은 폰트가 있으면 파일 다운로드 받아서 resources/static/fonts 경로에 추가하고 아래에 String값 한줄 추가
            List<String> resourceFonts = Arrays.asList(
                "static/font/NanumGothic.ttf",
                "static/font/JejuGothic.ttf",
                "static/font/폰트파일명"			// 이런식으로 한줄 추가하면됨
            );
            for (String path : resourceFonts) {
                try {
                    ClassPathResource resource = new ClassPathResource(path);
                    System.out.println("Try Load: " + path + " → " + resource.exists());
                    if (resource.exists()) {
                        fontPaths.add(resource.getFile().getAbsolutePath());
                    }
                } catch (IOException e) {
                    System.err.println("리소스 폰트 로드 실패: " + path + " → " + e.getMessage());
                }
            }

            
            // 최종 폰트 등록
            if (!fontPaths.isEmpty()) {
            	int seq = 1;
                for (String path : fontPaths) {
                    fontResolver.addFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                    BaseFont baseFont = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                    System.out.println(seq+++". 폰트 로드 성공: " + path);                    	   // 본인이 적용한 폰트가 잘 로드 되었는지 확인
                    System.out.println("Full Name: " + baseFont.getFullFontName()[0][3]);      // NanumGothic 여기에 나오는 이름으로 css에 font-family 적용
                }
            } else {
                System.out.println("한글 폰트를 찾을 수 없습니다. 기본 폰트를 사용합니다.");
            }

        } catch (Exception e) {
            System.err.println("폰트 설정 중 오류: " + e.getMessage());
        }
    }

}