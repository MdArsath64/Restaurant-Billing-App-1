use restaurent
select * from orders

//table creation
Create table orders(order_id int  primary key auto_increment ,dish_name varchar(225),quantity int ,
total_amount decimal(10,2) ,
order_date timestamp

select sum(total_amount) from orders where day(order_date)=16
select count(order_id) from orders  where day(order_date)=16
select sum(quantity) from orders where dish_name="dosa"
select dish_name,sum(quantity) from orders group by dish_name; 
