package pl.bgnat.hangman.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface GameRepository extends JpaRepository<Game, Long> {
}
