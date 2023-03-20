import controller.DeveloperController;
import controller.SpecialtyController;
import model.Developer;
import model.Skill;
import model.Specialty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeveloperControllerTest {
    @Mock
    private DeveloperController controller;

    private Specialty specialty1 = new Specialty(1,"Developer");
    private Specialty specialty2 = new Specialty(2,"JavaDeveloper");

    private Developer developer1 = new Developer(1,"Ivan","Petrov");
    private Developer developer2 = new Developer(2,"Igor","Igorov",specialty2,List.of(new Skill(1,"Delegate")));
    private Developer developer3 = new Developer(10,"Inna","Volkova",specialty1,List.of(new Skill(1,"Coding"),new Skill(3,"Testing")));

    @Test
    public void create() throws SQLException {
        controller.create(developer2);
    }

    @Test
    public void get() throws SQLException {
        when(controller.get(anyInt())).thenReturn(developer3);

        assertEquals(10,controller.get(10).getId().intValue());
        assertEquals("Inna",controller.get(100).getFirstName());
        assertEquals(2,controller.get(4).getSkills().size());
        assertEquals(specialty1, controller.get(1).getSpecialty());
    }


    @Test
    public void getAll() throws SQLException {
        List<Developer> developers = new ArrayList<>();
        developers.add(developer1);
        developers.add(developer2);
        developers.add(developer3);

        when(controller.getAll()).thenReturn(developers);

        assertEquals(3,controller.getAll().size());
        assertEquals(null,controller.getAll().get(0).getSpecialty());
        assertEquals(null,controller.getAll().get(0).getSkills());
    }

    @Test
    public void update() throws SQLException {
        when(controller.update(developer1)).thenReturn(developer1.getId());

        assertEquals(1,controller.update(developer1));
    }


    @Test
    public void delete() throws SQLException {
        controller.delete(anyInt());
    }

}
