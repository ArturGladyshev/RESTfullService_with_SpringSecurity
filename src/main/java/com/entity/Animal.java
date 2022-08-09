package com.entity;

import com.enums.AnimalEnum;
import com.enums.GenderEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "animals", uniqueConstraints =
	{
		@UniqueConstraint(columnNames = "nickname")
	}
)
public class Animal
{
		public Animal()
		{
		}

		public Animal(String nickname, Date birthday, AnimalEnum type, GenderEnum gender, String user)
		{
				this.nickname = nickname;
				this.birthday = birthday;
				this.type = type;
				this.gender = gender;
				this.username = user;
		}

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "animal_id")
		private long id;

		@Column
		private String nickname;

		@Column
		private Date birthday;

		@Column
		@Enumerated(EnumType.STRING)
		private AnimalEnum type;

		@Column
		@Enumerated(EnumType.STRING)
		private GenderEnum gender;

		@Column(name = "username")
		private String username;

		public long getId()
		{
				return id;
		}

		public void setId(long id)
		{
				this.id = id;
		}

		public String getNickname()
		{
				return nickname;
		}

		public void setNickname(String nickname)
		{
				this.nickname = nickname;
		}

		public Date getBirthday()
		{
				return birthday;
		}

		public void setBirthday(Date birthday)
		{
				this.birthday = birthday;
		}

		public AnimalEnum getType()
		{
				return type;
		}

		public void setType(AnimalEnum type)
		{
				this.type = type;
		}

		public GenderEnum getGender()
		{
				return gender;
		}

		public void setGender(GenderEnum gender)
		{
				this.gender = gender;
		}

		public String getUsername()
		{
				return username;
		}

		public void setUsername(String user)
		{
				this.username = user;
		}
}
