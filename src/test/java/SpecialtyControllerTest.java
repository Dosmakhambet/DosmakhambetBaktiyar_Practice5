import controller.SkillController;
import controller.SpecialtyController;
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
public class SpecialtyControllerTest {
    @Mock
    private SpecialtyController controller;

    private Specialty specialty1 = new Specialty(1,"Developer");
    private Specialty specialty2 = new Specialty(2,"JavaDeveloper");
    private Specialty specialty3 = new Specialty(2,"SeniorDeveloper");


    @Test
    public void create() throws SQLException {
        controller.create(specialty1);
    }

    @Test
    public void get() throws SQLException {
        when(controller.get(anyInt())).thenReturn(specialty1);

        assertEquals(1,controller.get(10).getId().intValue());
        assertEquals("Developer",controller.get(100).getName());
    }


    @Test
    public void getAll() throws SQLException {
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);
        specialties.add(specialty2);
        specialties.add(specialty3);

        when(controller.getAll()).thenReturn(specialties);

        assertEquals(3,controller.getAll().size());
        assertEquals("SeniorDeveloper",controller.getAll().get(2).getName());
    }

    @Test
    public void update() throws SQLException {
        when(controller.update(specialty1)).thenReturn(specialty1.getId());

        assertEquals(1,controller.update(specialty1));
    }


    @Test
    public void delete() throws SQLException {
        controller.delete(anyInt());
    }

}
