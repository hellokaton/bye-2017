package io.github.biezhi.bye2017;

import com.blade.Blade;
import com.blade.validator.ValidatorMiddleware;

public class Application {

    public static void main(String[] args) {
        Blade.me().use(new ValidatorMiddleware()).start(Application.class, args);
    }

}