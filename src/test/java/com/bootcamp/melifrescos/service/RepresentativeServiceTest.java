package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.model.Representative;
import com.bootcamp.melifrescos.repository.IRepresentativeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class RepresentativeServiceTest {

    @InjectMocks
    private RepresentativeService service;

    @Mock
    IRepresentativeRepo repository;

    private Representative represent;

    @BeforeEach
    void setUp() {
        represent = new Representative(1L, "Joaquim", "joca@email.com", "123.123.123-44", null);
    }

    @Test
    void create_returnRepresentative_whenPaylodSuccess() {
        Mockito.when(repository.save(ArgumentMatchers.any()))
                .thenReturn(represent);

        Representative newRepresenttive = service.create(represent);

        assertThat(represent.getName()).isEqualTo(newRepresenttive.getName());
        assertThat(represent.getEmail()).isEqualTo(newRepresenttive.getEmail());
        assertThat(represent.getCpf()).isEqualTo(newRepresenttive.getCpf());
    }

    @Test
    void getById_returnRepresentative_whenIdSuccess() {
        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(represent));

        Optional<Representative> getRepresentative = service.getById(1L);

        assertThat(represent.getName()).isEqualTo(getRepresentative.get().getName());
        assertThat(represent.getCpf()).isEqualTo(getRepresentative.get().getCpf());
        assertThat(represent.getEmail()).isEqualTo(getRepresentative.get().getEmail());
    }
}
