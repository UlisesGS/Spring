package com.libreria.springboot.backend.apirest.models.dao;

import com.libreria.springboot.backend.apirest.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
	
	public Usuario findByUsername(String username);

	/*public Usuario findByUsernameAndEmail(String username, String email); PARA HACERLO CON MAS PARAMETROS*/
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername2(String username);

	/*@Query("select u from Usuario u where u.username=?1 and u.otro=?2")
	public Usuario findByUsername2(String username, String otro);*/

}
