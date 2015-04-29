package com.pronet.Skills;

import com.pronet.Skills.skills;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SkillsRowMapper implements RowMapper
{
        public Object mapRow (ResultSet rs,int rowNum)throws SQLException {
            skills skill= new skills();
            skill.setSkillID(rs.getInt(1));
            skill.setSkillName(rs.getString(2));
            System.out.println(skill.getSkillID() +","+ skill.getSkillName());
            return skill;
    }
}
