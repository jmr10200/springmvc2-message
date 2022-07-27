package hello.itemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	/**
	 * 메시지 기능
	 */
	// 기획자가 갑자기 화면에 보이는 문구를 바꾸고 싶다며, '상품명' 이라는 단어를 모두 '상품이름'으로 바꿔달라고 한다.
	// 어떻게 하는 것이 좋은가?
	// 여러 화면에 보이는 상품명, 가격, 수량 등 label 로 지정한 모든 단어를 변경하려면, 각 화면을 모두 변경해야 할 것이다.
	// 지금과 같이 화면수가 적다면 문제가 되지 않을 수 있지만, 화면이 많으면 그 많은 화면을 모두 수정해야 한다.
	// 해당 HTML 파일에 메시지가 하드코딩 되어 있기 때문이다.
	// 이렇게 다양한 메시지를 한 곳에서 관리하도록 하는 기능을 메시지 기능 이라고 한다.
	// 예를들어 messages.properties 라는 메시지 관리용 파일을 만들고 아래와 같이 정의한다.
	// item = 상품
	// item.itemName = 상품명
	// item.price = 가격
	// 이제 각 HTML 에서 다음과 같이 해당 항목을 key 값으로 호출하여 사용하는 것이다.
	// <label for="itemName" th:field+"#{item.itemName}"></label>

	/**
	 * 국제화 기능
	 */
	// 메시지 파일 (messages.properties)을 각 나라별로 별도로 관리하면 서비스를 국제화 할 수 있다.
	// 예를들어 다음과 같이 파일을 만든다.
	// messages_ja.properties
	// item = 商品
	// item.itemName = 商品名
	// item.price = 価格
	// messages_en.properties
	// item = Item
	// item.itemName = Item Name
	// item.price = Price
	// messages_ko.properties
	// item = 상품
	// item.itemName = 상품명
	// item.price = 가격
	// 일본어 사용자는 messages_ja.properties, 영어 사용자는 messages_en.properties 를 사용하게 개발하면 된다.
	// 이렇게 정의하여 사이트를 국제화 할 수 있다.
	// 어느 나라에서 접근한 것인지 인식하는 방법은 HTTP accept-language 헤더 없을 사용하거나,
	// 사용자가 직접 언어를 선택하도록 하고 쿠키 등을 사용하여 처리하면 된다.

	// 이러한 메시지와 국제화 기능을 직접 구현할 수도 있겠지만, 스프링은 기본적인 메시지와 국제화 기능을 모두 제공한다.
	// 타임리프도 스프링이 제공하는 메시지와 국제화 기능을 편리하게 통합하여 제공한다.

	/**
	 * 스프링은 기본적인 메시지 관리 기능을 제공한다.
	 * 스프링부트는 application.properties 파일에 설정하면된다.
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		// 프로퍼티 파일을 등록
		// messages 지정 : messages.properties 파일을 읽는다.
		// 국제화 기능을 이용할 때는 파일명에 messages_en.properties 와 같이 파일명 마지막에 언어정보를 적는다.
		// 만약 찾을 수 있는 국제화 파일이 없으면 기본 파일 messages.properties 를 이용한다.
		// 파일 위치는 /resources/messages.properties
		// 여러 파일을 지정할 수도 있다. messages, errors
		messageSource.setBasenames("messages", "errors");
		// 인코딩 정보의 지정
		messageSource.setDefaultEncoding("utf-8");
		return messageSource;
	}
}
