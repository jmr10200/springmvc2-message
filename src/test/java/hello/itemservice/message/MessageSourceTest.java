package hello.itemservice.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    /**
     * 단순한 기본 messages 테스트
     */
    @Test
    void helloMessage(){
        // 메시지 코드로 hello 를 입력하고 나머지는 null 설정
        // locale 정보가 없으면 basename 에서 설정한 기본 이름 메시지 파일을 조회한다.
        // basename 으로 messages 를 지정했으므로 messages.properties 파일에서 데이터를 조회한다.
        String result = messageSource.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
        // MessageSource 는 인터페이스이다. 아래의 두 메소드를 이용할 수 있다.
        // getMessage(code, args, locale)
        // getMessage(code, args, defaultMessage, locale)
    }

    /**
     * 메시지가 없는 경우 1 (존재하지 않는 key), messages.properties
     * 메시지가 없으면 NoSuchMessageException 발생
     */
    @Test
    void notFoundMessageCode() {
        // Exception test : assertThatThrownBy()
        assertThatThrownBy(() -> messageSource.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    /**
     * 메시지가 없는 경우 2 , default messages 설정
     * 메시지가 없어도 defualtMessage 를 사용하면 기본 메시지가 반환된다.
     */
    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = messageSource.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    /**
     * 매개변수 사용 (args)
     */
    @Test
    void argumentMessage() {
        // hello.name = 안녕 {0}
        // args 사용하여 표시 가능하다.
        String result = messageSource.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    /**
     * 국제화 파일 선택 1
     * locale 정보를 이용
     */
    @Test
    void defaultLang() {
        // locale 정보가 null 인 경우, Locale.getDefault() 를 호출해서 시스템의 기본 로케일을 사용한다.
        // 기본 locale 이 ko_KR 이므로 messages_ko.properties 를 찾아 보지만,
        // 존재하지 않으므로 기본 값인 messages.properties 사용
        assertThat(messageSource.getMessage("hello", null, null)).isEqualTo("안녕");
        // locale 정보가 KOREA 이지만 messages_ko 가 없으므로 기본 값인 messages.properties 사용
        assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    /**
     * 국제화 파일 선택 2
     * locale 정보를 이용
     */
    @Test
    void enLang() {
        // locale 정보가 ENGLISH 이므로 messages_en.properties 을 찾아서 사용
        assertThat(messageSource.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
