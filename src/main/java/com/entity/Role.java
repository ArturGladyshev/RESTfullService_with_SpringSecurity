package com.entity;

import com.enums.RoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {


		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "role_id")
		private long id;

		@Enumerated(EnumType.STRING)
		@Column(length = 20)
		private RoleEnum name;

		public Role()
		{

		}

		public Role(RoleEnum name)
		{
				this.name = name;
		}
		public long getId()
		{
				return id;
		}

		public void setId(long id)
		{
				this.id = id;
		}

		public RoleEnum getName()
		{
				return name;
		}

		public void setName(RoleEnum name)
		{
				this.name = name;
		}

}
