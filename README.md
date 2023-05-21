# StudentDB
## О приложении
Было необходимо реализовать систему ввода и отображения информации о студентах института,
включающую следующие сущности и их атрибуты:
+ Студент

  + Имя
  + Фамилия
  + Отчество
  + Дата рождения
  + Группа
  
+ Группа

  + Номер
  + Название факультета
  
Система должна иметь следующие функции:
+ Отображение списка групп
+ Добавление новой группы, редактирование и удаление существующей
+ Отображение списка студентов
+ Фильтрация списка студентов по фамилии и по номеру группы
+ Добавление нового студента, редактирование и удаление существующего
+ Система должна иметь защиту от удаления группы, содержащей студентов
___
## Используемый стек
+ ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
+ ![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white)
+ Android SDK
+ Room
___
## Витрина скриншотов

Add a student             |  Add a group
:-------------------------:|:-------------------------:
<img src="screenshots/add_student.jpg" width="400" height="900"> </img> |<img src="screenshots/add_group.jpg" width="400" height="900"> </img>

List of students             |  List of groups
:-------------------------:|:-------------------------:
<img src="screenshots/student_list.jpg" width="400" height="900"> </img> |<img src="screenshots/group_list.jpg" width="400" height="900"> </img>

Update a student            |  Update a group
:-------------------------:|:-------------------------:
<img src="screenshots/update_student.jpg" width="400" height="900"> </img> |<img src="screenshots/update_group.jpg" width="400" height="900"> </img>

Filter students by surname           |  Filter students by group number
:-------------------------:|:-------------------------:
<img src="screenshots/filter_by_surname.jpg" width="400" height="900"> </img> |<img src="screenshots/filter_by_number.jpg" width="400" height="900"> </img>

Attempt to delete group with students    
:-------------------------:
<img src="screenshots/try_to_delete_group_with_students.jpg" width="400" height="900"> </img>

<!-- <p align="center">
  <img src="screenshots/add_student.jpg" width="540" height="1100">
  <img src="screenshots/add_group.jpg" width="540" height="1100">
  <img src="screenshots/student_list.jpg" width="540" height="1100">
  <img src="screenshots/group_list.jpg" width="540" height="1100">
  <img src="screenshots/update_student.jpg" width="540" height="1100">
  <img src="screenshots/update_group.jpg" width="540" height="1100">
  <img src="screenshots/filter_by_number.jpg" width="540" height="1100">
  <img src="screenshots/filter_by_surname.jpg" width="540" height="1100">
  <img src="screenshots/try_to_delete_group_with_students.jpg" width="540" height="1100">
</p>
 -->
