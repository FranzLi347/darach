//package io.github.franzli347.darach.service.impl;
//
//import io.github.franzli347.darach.mapper.AnimateMapper;
//import io.github.franzli347.darach.model.dto.AnimateCreateDto;
//import io.github.franzli347.darach.model.dto.EpisodeDto;
//import io.github.franzli347.darach.service.VedioPathService;
//import io.github.franzli347.darach.utils.XxlJobTrigger;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.lang.reflect.Field;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@Slf4j
//class AnimateServiceImplTest {
//
//    private AnimateServiceImpl animateServiceImplUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        // mock the dependencies
//        animateServiceImplUnderTest = new AnimateServiceImpl();
//        AnimateMapper mockAnimateMapper = mock(AnimateMapper.class);
//        when(mockAnimateMapper.insert(any())).thenReturn(1);
//        when(mockAnimateMapper.selectById(any())).thenReturn(null);
//        //反射设置mapper
//        try {
//            Field baseMapper = animateServiceImplUnderTest.getClass().getSuperclass().getDeclaredField("baseMapper");
//            baseMapper.setAccessible(true);
//            baseMapper.set(animateServiceImplUnderTest, mockAnimateMapper);
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        when(animateServiceImplUnderTest.save(any())).thenAnswer(invocationOnMock -> 1);
//        VedioPathService mockVedioPathService = mock(VedioPathService.class);
//        when(mockVedioPathService.saveBatch(any())).thenReturn(true);
//        animateServiceImplUnderTest.vedioPathService = mockVedioPathService;
//        XxlJobTrigger mockXxlJobTrigger = mock(XxlJobTrigger.class);
//        when(mockXxlJobTrigger.triggerJob(any())).thenAnswer(invocationOnMock -> "success");
//        animateServiceImplUnderTest.xxlJobTrigger = mockXxlJobTrigger;
//    }
//
//    @Test
//    void testAddNewAnimate() {
//        log.debug("testAddNewAnimate");
//        final AnimateCreateDto dto = new AnimateCreateDto();
//        dto.setName("name");
//        dto.setDisplayImgUrl("displayImgUrl");
//        final EpisodeDto episodeDto = new EpisodeDto();
//        episodeDto.setEpisode(0);
//        episodeDto.setFileName("fileName");
//        episodeDto.setEpisode(2);
//        episodeDto.setFileName("fileName2");
//        dto.setEpisodeList(List.of(episodeDto));
//        log.debug("dto:{}",dto);
//        // Run the test
//        final Boolean result = animateServiceImplUnderTest.addNewAnimate(dto);
//        log.debug("result:{}",result);
//        // Verify the results
//        assertThat(result).isTrue();
//    }
//
//}
