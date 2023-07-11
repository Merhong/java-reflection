package ex03;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)           // 어노테이션 범위 : 타입
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 유지 : 런타임
public @interface Controller {

}
