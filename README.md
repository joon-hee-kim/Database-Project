# Database-Project
This is the result of a JDBC project created using Java Eclipse and MySQL.

## Motivation
In a situation where orphanage leavers and those requiring guardians continue to experience economic difficulties, and in South Korea, where labor shortages are worsening as we enter a super-aging society, the introduction of a job matching system for orphanage leavers is an urgent task.

## Output
**Step 1. Data Curation** (Reference to **Data Source**)</br></br>
**Step 2. Data Preprocessing** (Reference to **Algorithm**)</br></br>
**Step 3. Modeling** </br>

- #1. Perform regional latitude/longitude-based clustering using the KMeans algorithm </br>
<img width="452" alt="그림1" src="https://github.com/joon-hee-kim/ML-Driven-Optimization-of-Childcare-Allocation-and-Labor-Demand/assets/121689436/e458c6df-a820-48ad-9f52-195c60e54d0b"> </br>


- #1. Using K-Means algorithm </br>
#2. Perform clustering based on cluster latitude/longitude and labor values </br>
<img width="452" alt="그림2" src="https://github.com/joon-hee-kim/ML-Driven-Optimization-of-Childcare-Allocation-and-Labor-Demand/assets/121689436/548f621a-0517-4d71-92f3-43bf14cc9df1"> </br>


**Step 4. Analysis and visualization** </br>

- By looking at the visualized data, you can see which regions have a low labor demand ratio (Red and green areas represent high and low labor demand, respectively). </br>
- Therefore, population should be allocated to these regions first. </br>
<img width="452" alt="그림3" src="https://github.com/joon-hee-kim/ML-Driven-Optimization-of-Childcare-Allocation-and-Labor-Demand/assets/121689436/c426552d-7d74-4470-acc6-2920c49f1822"> </br>


**Step 5. Childcare facility matching**</br>

- Distance calculation </br>
- The figure below shows the step for calculating the distance between child care facilities and labor demand areas. </br>
<img width="452" alt="그림4" src="https://github.com/joon-hee-kim/ML-Driven-Optimization-of-Childcare-Allocation-and-Labor-Demand/assets/121689436/f06ae4bf-b01b-49f9-9dd0-64695f602ed1"> </br>


- Ranking calculation </br>
<img width="114" alt="그림5" src="https://github.com/joon-hee-kim/ML-Driven-Optimization-of-Childcare-Allocation-and-Labor-Demand/assets/121689436/a1a685b7-2896-48f6-a732-2b242eab4db5"> </br>
- This figure shows ranking calculations based on distance and labor demand ratio. Each cluster is ranked, which helps determine which areas should receive priority attention for employment placement. </br>
- The value in the “노동수치_군집” column represents the unique number of each cluster, and the value in the “배치” column represents the rank in which each cluster will be assigned labor. For example, Cluster 0 ranks 81st to receive labor. </br>


**Final result** </br>
- The figure below is 3 childcare centers closest to cluster number 49 as an example. </br>
(The value in the “노동수치 군집 번호, 보육 시설, 거리” column means Labor figures "Cluster number, childcare facilities, distance".) </br>
<img width="549" alt="스크린샷 2024-06-16 오후 11 46 19" src="https://github.com/joon-hee-kim/ML-Driven-Optimization-of-Childcare-Allocation-and-Labor-Demand/assets/121689436/2b81761b-e5da-42cd-b476-31cf0dd4408a"> </br></br>


## Algorithm


## 👥 Team Member

201934219 Kim Joonhee </br>

 
## ✔️ Source
* Github Link that we refered: [Reference Link](https://github.com/SongChiyoon/twitter/tree/master) </br>
