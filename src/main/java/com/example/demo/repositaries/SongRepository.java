package com.example.demo.repositaries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {

	public Song findByName(String name);

}
