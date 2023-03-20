import controller.SkillController;
import model.Skill;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SkillControllerTest {
    @Mock
    private SkillController controller;

    private Skill skill1 = new Skill(1,"Test");
    private Skill skill2 = new Skill(2,"Test2");


    @Test
    public void create() throws SQLException {
        controller.create(skill2);
    }

    @Test
    public void get() throws SQLException {
        when(controller.get(anyInt())).thenReturn(skill1);

        assertEquals(1,controller.get(10).getId().intValue());
        assertEquals("Test",controller.get(100).getName());
    }


    @Test
    public void getAll() throws SQLException {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill(2,"TestAll"));
        skills.add(skill1);

        when(controller.getAll()).thenReturn(skills);

        assertEquals(2,controller.getAll().size());
        assertEquals("TestAll",controller.getAll().get(0).getName());
    }

    @Test
    public void update() throws SQLException {
        when(controller.update(skill1)).thenReturn(skill1.getId());

        assertEquals(1,controller.update(skill1));
    }


    @Test
    public void delete() throws SQLException {
        controller.delete(anyInt());
    }

}
