package org.learning.platform;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.learning.platform.dto.UserHolder;
import org.learning.platform.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Slf4j
@SpringBootTest
public class FormatMethodTest {
    @Test
    public void smallTest() throws FileNotFoundException {
        int []a={1,2,3};
        System.out.println(Arrays.asList(a));
    }
}
