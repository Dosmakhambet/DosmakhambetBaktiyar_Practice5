import com.dosmakhambetbaktiyar.model.Developer;
import com.dosmakhambetbaktiyar.model.Skill;
import com.dosmakhambetbaktiyar.model.Specialty;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.dosmakhambetbaktiyar.repository.DeveloperRepository;
import com.dosmakhambetbaktiyar.service.DeveloperService;
import com.dosmakhambetbaktiyar.service.impl.DeveloperServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeveloperServiceTest {

    private DeveloperService service;

    @Mock
    private DeveloperRepository repository;

    private Specialty specialty1 = new Specialty(1,"Developer");
    private Specialty specialty2 = new Specialty(2,"JavaDeveloper");

    private Developer developer1 = new Developer(1,"Ivan","Petrov");
    private Developer developer2 = new Developer(2,"Igor","Igorov",specialty2,List.of(new Skill(1,"Delegate")));
    private Developer developer3 = new Developer(10,"Inna","Volkova",specialty1,List.of(new Skill(1,"Coding"),new Skill(3,"Testing")));


    @Before
    public void setUp(){
        service = new DeveloperServiceImpl(repository);
    }

    @Test
    public void create(){
        when(service.create(any(Developer.class))).thenReturn(developer2);

        Developer developer = service.create(new Developer(2,"Bill","Gates"));

        assertEquals(2,developer.getId().intValue());
        assertEquals(specialty2,developer.getSpecialty());
        assertEquals("Igor",developer.getFirstName());
        assertEquals("Igorov",developer.getLastName());
        assertEquals(1,developer.getSkills().size());
    }

    @Test
    public void get(){
        when(service.get(anyInt())).thenReturn(developer3);

        Developer developer = service.get(2);

        assertEquals(10,developer.getId().intValue());
        assertEquals(specialty1,developer.getSpecialty());
        assertEquals("Inna",developer.getFirstName());
        assertEquals("Volkova",developer.getLastName());
        assertEquals(2,developer.getSkills().size());
        assertEquals("Coding",developer.getSkills().get(0).getName());
    }


    @Test
    public void getAll(){


        when(service.getAll()).thenReturn(getDevelopers());

        List<Developer> developers = service.getAll();

        assertEquals(3,developers.size());
        assertNull(developers.get(0).getSpecialty());
        assertNull(developers.get(0).getSkills());
    }

    @Test
    public void update(){
        when(service.update(any(Developer.class))).thenReturn(developer1);

        Developer developer = service.update(new Developer(5,"Jah","Khalib"));

        assertEquals(1,developer.getId().intValue());
        assertEquals("Ivan",developer.getFirstName());
        assertEquals("Petrov",developer.getLastName());
    }


    @Test
    public void delete(){
        when(service.delete(anyInt())).thenReturn(true);

        boolean ok = service.delete(3);

        assertTrue(ok);
    }

    private List<Developer> getDevelopers(){
        List<Developer> developers = new ArrayList<>();
        developers.add(developer1);
        developers.add(developer2);
        developers.add(developer3);

        return developers;
    }
}
