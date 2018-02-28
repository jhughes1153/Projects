CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_into_tabel`(
IN ticker varchar(5),
IN mktCap varchar(10),
IN EnterpriseV varchar(10),
IN PE varchar(10),
IN PriceS varchar(10),
IN PriceB varchar(10),
IN EVEB varchar(10),
IN revPerShare varchar(10),
IN dilEps varchar(10),
IN totalC varchar(10),
IN totalD varchar(10),
IN bookVal varchar(10),
IN sharesOut varchar(10),
IN compet1 varchar(5),
IN compet2 varchar(5),
IN compet3 varchar(5))
BEGIN

	INSERT INTO FinancailInfo (stockTicker, marketCap, EV, trailingPE, Ps, PB, EVEBITDA, revenuePerShare,
    dilutedEPS, totalCash, totalDebt, bookValuePerShare, sharesOutstanding, comp1, comp2, comp)
    VALUES (ticker, mktCap, EnterpriseV, PE, PriceS, PriceB, EVEB, revPerShare, dilEps, totalC, totalD,
    bookVal, sharesOut, compet1, compet2, compet3);
END
