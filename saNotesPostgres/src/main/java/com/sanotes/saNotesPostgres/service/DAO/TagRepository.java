package com.sanotes.saNotesPostgres.Service.DAO;

import com.sanotes.saNotesPostgres.Service.Model.TagModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<TagModel, Long> {
    @Query("select t from TagModel t where t.name like ?1")
    List<TagModel> findByName(String name, Pageable pageable);
}