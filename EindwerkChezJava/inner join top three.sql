select idober, tblober.firstname, tblober.lastName, sum(totalprice) as sumtotalprice
from(
	Select tblorderlqd.idober as idober, price * qty as totalprice
	from(
		Select orders.idOber as idober, t.price as price, orders.qty as qty
		from tblorders orders
		left join tblliquids t on t.idLiquid= orders.idLiquid
	) as tblorderlqd
) as tblsumober
Inner join tblober ON tblober.id = idober
group by idober
Order by sumtotalprice DESC 
LIMIT 3

