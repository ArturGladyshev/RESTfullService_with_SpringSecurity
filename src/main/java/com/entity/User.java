package com.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints =
	{
		@UniqueConstraint(columnNames = "username")
	}
)
public class User
{
		public User()
		{
		}

		public User(String username, String password, List<Animal> animals)
		{
				this.username = username;
				this.password = password;
				this.animals = animals;
		}


		public User(String name, String password)
		{
				this.username = name;
				this.password = password;
		}

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "user_id")
		private long id;

		@Column
		private String username;

		@Column
		private String password;

		@OneToMany
		@JoinColumn(name = "username")
		private List<Animal> animals;

		@ManyToMany(fetch = FetchType.LAZY)
		@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
		private Set<Role> roles;

		public long getId()
		{
				return id;
		}

		public void setId(long id)
		{
				this.id = id;
		}

		public String getUsername()
		{
				return username;
		}

		public void setUsername(String name)
		{
				this.username = name;
		}

		public String getPassword()
		{
				return password;
		}

		public void setPassword(String password)
		{
				this.password = password;
		}

		public List<Animal> getAnimals()
		{
				return animals;
		}

		public void setAnimals(List<Animal> animals)
		{
				this.animals = animals;
		}

		public Set<Role> getRoles()
		{
				return roles;
		}

		public void setRoles(Set<Role> roles)
		{
				this.roles = roles;
		}
}
