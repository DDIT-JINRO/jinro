package kr.or.ddit.ertds.univ.uvsrch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.ertds.univ.uvsrch.service.UniversityDetailVO.DeptInfo;
import kr.or.ddit.ertds.univ.uvsrch.service.UniversityManageService;
import kr.or.ddit.ertds.univ.uvsrch.service.UniversityVO;

@Service
public class UniversityManageServiceImpl implements UniversityManageService {
	
	@Autowired
	UniversityMapper universityMapper;

	@Override
    public int updateUniversity(UniversityVO universityVO) {
        return universityMapper.updateUniversity(universityVO);
    }

    @Override
	@Transactional
    public int deleteUniversity(int univId) {
        universityMapper.deleteDepartmentsByUnivId(univId);
        return universityMapper.deleteUniversity(univId);
    }

    @Override
    public UniversityVO selectUniversityById(int univId) {
        return universityMapper.selectUniversityById(univId);
    }

    @Override
    public int updateDepartment(DeptInfo deptInfo) {
        return universityMapper.updateDepartment(deptInfo);
    }

    @Override
    public int deleteDepartment(int udId) {
        return universityMapper.deleteDepartment(udId);
    }

    @Override
    public int deleteDepartmentsByUnivId(int univId) {
        return universityMapper.deleteDepartmentsByUnivId(univId);
    }

    @Override
    public DeptInfo selectDepartmentById(int udId) {
        return universityMapper.selectDepartmentById(udId);
    }

}
