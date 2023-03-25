import com.dosmakhambetbaktiyar.model.Skill;
import com.dosmakhambetbaktiyar.model.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import com.dosmakhambetbaktiyar.repository.SkillRepository;
import com.dosmakhambetbaktiyar.service.SkillService;
import com.dosmakhambetbaktiyar.service.impl.SkillServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SkillServiceTest {
    private SkillService service;

    @Mock
    private SkillRepository skillRepository;

    private Skill skill1 = new Skill(1,"Test");
    private Skill skill2 = new Skill(2,"Test2");
    private Skill skill3 = new Skill(10,"Cook");


    @Before
    public void setUp(){
        service = new SkillServiceImpl(skillRepository);
    }

    @Test
    public void create() {

        when(service.create(any(Skill.class))).thenReturn(skill1);

        Skill skill = service.create(new Skill("Clean"));

        assertEquals(1,skill.getId().intValue());
        assertEquals("Test",skill.getName());
        assertEquals(Status.ACTIVE,skill.getStatus());
    }

    @Test
    public void get(){

        when(service.get(anyInt())).thenReturn(skill2);

        Skill skill = service.get(10);

        assertEquals(2,skill.getId().intValue());
        assertEquals("Test2",skill.getName());
        assertEquals(Status.ACTIVE, skill.getStatus());
    }


    @Test
    public void getAll() {
        List<Skill> skills = getSkills();

        when(service.getAll()).thenReturn(skills);

        List<Skill> skillList = service.getAll();

        assertEquals(3,skillList.size());
        assertEquals(Status.ACTIVE, skillList.get(2).getStatus());
    }

    @Test
    public void update() throws SQLException {
        when(service.update(any(Skill.class))).thenReturn(skill3);

        Skill skill = service.update(skill1);

        assertEquals(10,skill.getId().intValue());
        assertEquals("Cook",skill.getName());
        assertEquals(Status.ACTIVE, skill.getStatus());
    }


    @Test
    public void delete() throws SQLException {
       when(service.delete(anyInt())).thenReturn(true);

       boolean ok = service.delete(skill1.getId());

       assertTrue(ok);
    }

    private List<Skill> getSkills(){
        List<Skill> list = new ArrayList<>();

        list.add(skill1);
        list.add(skill2);
        list.add(skill3);

        return list;
    }
}
