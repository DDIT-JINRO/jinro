package kr.or.ddit.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.TestVO;

@Mapper
public interface TestMapper {

	List<TestVO> test();

}
