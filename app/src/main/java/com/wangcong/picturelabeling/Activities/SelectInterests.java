package com.wangcong.picturelabeling.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcong.picturelabeling.R;
import com.wangcong.picturelabeling.Utils.ActivityCollector;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.Gravity.CENTER;

/**
 * Created by 13307 on 2017/4/22.
 */

public class SelectInterests extends AppCompatActivity {
    private String[] data_level_1 = {"经济金融", "企业管理", "法规法律", "社会民生", "科学教育", "健康生活", "体育运动", "文化艺术", "电子数码", "电脑网络", "娱乐休闲", "心理分析", "医疗卫生"};
    private String[][] data_level_2 = {{"经济", "银行", "基金", "金融", "证券", "黄金", "外汇", "期货", "保险", "贸易", "财政", "商业", "房地产"}, {"企业管理", "人力资源", "财务", "市场营销", "创业", "融资"}, {"法律", "婚姻继承", "劳动人事", "交通事故", "医疗纠纷", "财产房产", "知识产权", "权益维护", "刑事案件", "公司事务", "债务债权"}, {"军事", "宗教", "时事政治", "办公", "职业"}, {"教育", "天文", "地球科学", "物理", "农业", "生物", "社会学", "培训", "数学", "科学技术", "环境学", "心理学", "职业教育", "升学入学", "化学", "外语学习", "医学", "语文", "纺织", "建筑学", "出国留学"}, {"烹饪", "装饰", "健身", "宠物", "汽车", "民俗", "交通", "生活", "健康知识", "购物", "育儿"}, {"球类运动", "冰雪运动", "足球运动", "智力运动", "武术", "体育运动", "体育赛事"}, {"绘画", "工艺品", "历史", "视觉设计", "文学", "时尚", "书法", "艺术"}, {"数码", "智能设备"}, {"电脑", "软件应用", "互联网", "通讯技术", "IT"}, {"游戏", "博彩", "收藏", "旅游", "占卜", "动漫", "影视", "明星", "舞蹈", "音乐", "品鉴", "娱乐", "乐器"}, {"烦恼", "爱情", "家庭关系", "两性问题", "校园生活"}, {"医疗", "健康", "妇产科", "皮肤科", "中医", "五官科", "儿科", "内分泌科", "内科", "肿瘤科", "传染科", "人体常识", "男性泌尿科", "外科", "精神心理科"}};
    private String Interests = "经济:p2p,人民币,印花税,员工关系,大宗商品,安全员,经济;银行:P2P,个人理财,中国银行,信托,信用卡,信用卡套现,信用卡还款,信用社,借记卡,储蓄,光大银行,农业银行,农信社,农信社考试,农商行,农村信用社招聘,利率,商贷,定期存款,工商银行,建行,建设银行,担保,招商银行,按揭,支付,支付宝,收益,收银机,期汇,活期存款,理财,理财产品,笔试,网银,考试,贷款,贷记卡,转账,还款,邮储银行,银行,银行业务,银行从业,银行卡,银行实习,银行开户,银行承兑汇票,银行校园招聘,银行流水,银行理财,银行网申,银行考试;基金:余额宝,债券基金,公募基金,基金,天天基金,平衡型基金,指数基金,活期宝,合型基金,理财基金,理财通,私募基金,股票基金,货币市场基金,赎回,趣分期;金融:P2P贷款,WTO,互联网理财,互联网金融,广品众筹,众筹,你我贷,借贷宝,刷卡支付,国际信用,国际投资,国际收支,国际结算,国际货币体系,国际金融,央行,奖励众筹,定向增发,开户,微信支付,微商代理,拉卡拉,权益众筹,来分期,汇票,消费众筹,经营性众筹,网络理财,网贷,股指期货,股权众筹,金融,金融营销,钱袋宝,陆金所;证券:a股,三板,个股,中小企业融资,债券,做市,大盘,新三板,新股,港股,炒股,美股,股价,股市,股权投资,股票,股票公式,股票开户,证券,证券从业,财物证券,货币证券,资本证券,选股;黄金:干足金,现货白银,现货黄金,白银,白银TD,纸白银,纸黄金,贵金属,黄金,黄金TD,黄金交易;外汇:外汇,外汇交易,外汇平台,现汇;期货:农产品期货,利率期货,外汇期货,有色金属期货,期权,期货,股票期货,能源期货;保险:交强险,人寿保险,保险,保险公司,保障险,健康保险,健康险,儿童保险,养老保险,分红保险,划痕险,医保,医疗保险,商业保险,女性保险,家庭财产险,少儿保险,少儿险,工伤保险,平安保险,意外保险,意外险,机动车辆保险,海外保险,玻璃险,理赔,社会保险,社保,第三方责任险,综合保险,车险,香港保险;贸易:dhl,中通,出口,商业合同,商品交易,国际小包,国际快递,国际物流,圆通,德邦,报关,汇通,海运,物流信息,物流平台,现货,现货原油,进口,进口食品;财政:企业所得税,使用税,关税,占用税,国税,增值税,契税,房产税,所得税,消费税,税收,营业税,财务,财政;商业:p2p理财,pos机,一件代发,专利申请,产业信息,会计师事务所,分销,刷单,刷卡机,加盟代理,加盟网店,原油投资,商业模式,商业,地税,女装加盟,工商代理,广告灯箱,店铺推广,开网店,微信分销,微信推广,微商货源,微商赚钱,投资,推广,收款机,新华油,新增,涂料加盟,消费机,淘宝加盟,淘宝开店,灯箱,物流公司,现货铜,理财软件,电子商务,电话营销,电销,短信群发,移动营销,童装,网信理财,网商,网店,网店加盟,网店推广,网站推广,股权激励,营销,营销推广,营销策划,融资租赁,西南大宗,财税,跨境电商,邮币卡,阿里巴巴,顶峰助力微营销,风投;房地产:买房,二手房,住房公积金,保障性住房,保障房,公租房,公积金,写字楼,农村别墅,别墅,北京房产,卖房,大产权,小产权,廉租房,房产,房产中介,房产证,房产过户,房价,房地产,房子,房屋,房贷,月供,楼盘,物业,物业管理,租房,经济适用房,自建房,购房,购房置业;企业管理:3M公司,仓库,企业年检,企业管理,外资公司,库存,招投标,海外公司,现场管理,组织机构代码,营业执照,询价,质量管理,采购,香港公司;人力资源:人事,人力资源,人力资源管理,人才管理,企业文化,劳动合同,升职加薪,招聘,招聘面试,离职,管理咨询,绩效考核,考勤,职称评审,薪资福利,行政管理;财务:个人所得税,个税,会计,分配,增值税,审计,报税,报表,收入,权益,核算,福利,税务,税务登记,税率,纳税,营业税,负债,财务,财务会计,财务管理,财政预算,资产评估,金蝶,金蝶软件;市场营销:品牌包装,品牌营销,市场营销,市场调研,渠道,顾客服务;创业:代理,创业,创业培训,加盟,商标注册,干洗店,生意,项目;融资:内部融资,外部融资,股权融资,融资,融资担保,责权融资;法律:人权,保险法,刑事,刑法,劳动法,劳动纠纷,司法,婚姻法,官司,宪法,律师,律师咨询,房产纠纷,故意伤害,民事纠纷,民商法,民法,法律,法律咨询,法律法规,法律问题,法律顾问,法规,法院,看守所,知识产权法,税法,纠纷,经济法,经济纠纷,缓刑,网络诈骗,行政法,警察,诉讼,诉讼法,轻伤,违法,逮捕,醉酒驾驶;婚姻继承:二胎,亲子鉴定,共同财产,出轨,婚前财产,婚后财产,婚姻继承,家暴,户口,房产,房贷,收养,离婚,结婚年龄,继承,财产公证,赔偿金,赡养,超生,过户,遗产,遗产继承,遗瞩,遗赠,重婚,骗婚;劳动人事:不竞争协议,事假,五险一金,仲裁,伤残鉴定,保密协议,公积金,养老保险,加班,劳务派遣,劳动争议,劳动人事,劳动仲裁,劳动关系,劳动合同,劳动检查,劳资纠纷,医保,失业保险,实习,工伤,工资,年假,撤职,病假,社保,社保卡,离职,竞业限制,解雇,试用期,转正,辞职,辞退,退休;交通事故:交通事故,交通事故争议,交通事故调解,交通事故赔偿,撞人,撞车,无证驾驶,罚单,肇事,肇事逃逸,责任认定,车祸,违章,逆行,酒驾;医疗纠纷:医疗事故争议,医疗纠纷,医疗维权,医疗赔偿,医疗过错,医疗鉴定,误诊;财产房产:占地,宅基地,拆迁,财产房产,财产纠纷;知识产权:专利,发明权,发现权,品牌,商标,商标代理,商标申请,商标转让,注册商标,版权,版权登记,知识产权,维权,署名权,著作权,软件著作权,马德里商标;权益维护:侵权,假冒伪劣产品,合同纠纷,名誉权,权益维护,消费者维权,物权纠纷,肖像权,诽谤,造谣,隐私权;刑事案件:上庭,上诉,交通肇事,传销,偷渡,再审,减刑,刑事拘留,刑事案件,刑事诉讼,刑事辩护,卖淫,双规,取保候审,芰贿,吸毒,围殴,坐牢,开庭,强奸,打伤,抢劫,拘留,拘禁,挪用公款,挪用资金,斗殴,正当防卫,殴打,毒品,犯罪,猥亵,盗窃,索贿,绑架,伤人,证据,诈骗,谋杀,贩毒,贪污,赌博,走私,起诉,辩护,重审;公司事务:不正当竞争,停牌,偷税,公司事务,公司法,兼并,商业合同,商业秘密,开公司,收购,改制,注册公司,漏税,破产,破产清算,股权转让,营业执照,行政诉讼,资产拍卖,重组,非法经营;债务债权:信用卡欠款,借据,借条,借款,债务,债务债权,债务纠纷,债权,债权债务,催款,工程款,拖欠工资,拖欠货款,欠债,人示人,欠钱不还,民间借贷,盗刷信用卡,讨债,还款,还钱,追债,非法集资,项目尾款,高利贷;军事:仿真枪,军事,军事历史,军事理论,军人,军刀,军舰,军衔,参军,导弹,当兵,战争,战斗机,战机,抗日,抗日战争,武器装备,武警,海军,潜艇,空军,航母,航空母舰,舰船,舰队,解放军,转业,退伍,退伍军人,部队,铅弹,铅弹枪;宗教:上帝,伊斯兰教,佛像,佛学,佛教,佛教用品,佛法,佛经,信佛,因果,圣经,地藏经,基督,基督徒,基督教,大悲咒,天主,天主教,学佛,宗教,密宗,开光,心灵法门,心经,念经,拜佛,教会,本命佛,梵文,楞严经,烧香,禅,禅宗,耶稣,般若,菩提,菩提子,菩萨,观世音菩萨,观音,观音菩萨,轮回,道教,金刚经,金刚菩提,阿弥陀佛;时事政治:反腐,时事,时事政治,时政,民族,马航;办公:公务办理,办公,思高文具,报事贴,文件柜,稳压器,稳压电源,粉碎机;职业:催乳师,升职加薪,奖金制度,打字员,招标师,求职,求职就业,淘宝兼职,淘宝美工,淘宝运营,监理工程师,简历,职业,职业发展,职业规划,职业资格,职场,职称评定,育婴师,警察,面试,高级职称;培训:ACCA,CFA,CMA,CPA,FRM,GRE,PMP,SAT,UI设计,UI设计培训,android,android培训,app开发,c语言培训,ios培训,java培训,中公教育,中级职称,事业单位考试,企业管理,会计,会计从业,会计硕士,会计职称,俄语,内审师,出国,出国培训,创业培训,初级职称,医师资格证,国考,培训学校,基金从业,夏令营,学习培训,审计,家教,小语种,少几英语,工作技能培训,建筑设计,建造师,弱电工程,意大利语,托福,执业医师,技能培训,投资分析师,护士资格证,护师,招警考试,教学,教师,教师资格考试,教师资格证,教育培训,教育硕士,新东方,新东方在线,新东方烹饪学校,新东方英语,新概念,日语,普通话,服装设计,朗阁,期货,法语,注册会计师,注册税务师,消防工程师,生活技能培训,申论,电气工程师,电焊,电脑学校,硬件工程师,科目一,科目三,科目四,移民,管理会计师,精算师,经济师,网络教育,美国,美国CPA,美术培训,考研,考研复试,考研政治,职业培训,职称英语,职称计算机,自考,英语职称,葡萄牙语,行测,西班牙语,计算机,证券,财务,财务培训,财经,资格考试,软件工程师,辅导班,造价员,酒店管理,金融考试,钳工证,铲车证,银行,阿拉伯语,雅思,韩语,项目管理,驾校,驾照考试,高级教师,高级职称;教育:单位招聘,作文,历史,地理,毕业证,丰图教育,华图网校,南开大学,南纬,博士,卫星电视,历史地理,历史题,压路机,厨师证,发生学,变电站,口译,古建筑,古诗文,同步课堂,向量,四年级,四边形,在线教育,在职研究生,地坪漆,地理信息系统,塑料管,塔吊证,外星人,外语,大专,大学化学,夸张,学技术,学而思,孩子教育,家庭教师,家庭教育,小升初,工程制图,工程技术,建筑工程,开题,开题报告,思埠,恒星,成人教育,成教,成考,打标机,托福考试,技术学校,技校,投光灯,招生,挤出机,排列组合,教育,教育机构,数学,文物,文科,文章,新加坡留学,新闻学,方管,期刊论文,本科,本科学历,机械制图,概率论,榨油,武汉大学,武汉理工大学,气象,汽修,汽修学校,汽枪,汽车学校,汽车美容,汽轮机,法学,法硕,浙江大学,湖南大学,滴胶,澳洲留学,灌溉设备,烘箱,物流管理;地球科学:冰川,地图,地球科学,地理,地理信息,地貌学,地质学,大地测量,大气科学,摄影测量与遥感,测绘,海洋,环境科学,纬度,纬线,经度,经纬,经纬度,自然地理;农业:养殖,农业,农业工程,农业机械,冲施,化肥,叶面肥,园林,土壤,土豆,土鸡,山鸡,施肥,有机肥,有机食品,果树,柿子,水产,水溶肥,水稻,滴灌,灌溉,烟草,玉米,畜牧兽医,番茄,种植,粮食,肉食鸡,肉鸡,肥料,芒果,古采,早每,葡萄,葫芦,疏采,蘑菇,蛋鸡,虫筆,野生菌,金银花,饲料,香菇,黄瓜;物理:二极管,中学物理,初中物理,初二物理,力学,功率,变压器,地球物理学,大学物理,天体物理学,干式变压器,应用物理学,控制器,材料力学,流体力学,温控器,热力学,物理,物理学,物理题,电压,电学,电流,电路设计,结构力学,继电器,调压器,调整器,量子,量子力学,高中地理,高中物理;天文:地球,天文,天文学,太阳,宇宙,望远镜,水星,火星,神舟,航空宇航,行星,行星科学,金星,银河系天文学,黑洞;生物:初一生物,初中生物,古生物,天体生物学,寄生虫学,微生物学,植物学,海洋生物,生态学,生物,生物化学,生物学,生理学,细胞生物学,蛋白线,解剖学,进化生物学,遗传学,食用菌,高一生物,高中生物,高二生物;社会学:人文,传播学,伦理学,哲理,哲学,国际关系学,思修,政治,政治学,教育学,犯罪学,社会学,管理学,经济学,语言学,马克思主义,马克思主义哲学;数学:Maple,Mathematica,三角函数,三角形,不定积分,不等式,中学数学,乘法,代数,倍数,公倍数,公因数,几何,函数,初一数学,初中数学,加法,因式,因式分解,因数,大学数学,大米,奥数,定积分,小学奥数,小学数学,小数,巧算,应用数学,应用题,微分方程,微积分,数列,数学,数学公式,数学分析,数学建模,数学物理,数学竞赛,数学问题,数学题,数独,整数,方程,极值,根号,比例尺,相对论,矩阵,离散数学,简便计算,简便运算,简算,算术,线性代数,自然数,行列式,解方程,质数,除以,高中数学,高考数学;科学技术:PLC,hdpe,hifi,h型钢,led,led灯,pe管,ppr,三视图,三通阀,二保焊机,二极管,五金,传感器,保温材料,写真机,冷风机,净化器,减速机,动力工程,包装机,医疗器械,半导体技术,卫星,印刷,压力管道,压缩机,发电机,发电机组,变压器与磁电,变频器,合金弹头,吊车,四氟,园林设计,土木工程,坦克,塑料管材,塑胶,复合管,太空,太阳能电池,安防系统,定位,射频,工业设计,工作站,工程学,工程机械,工程测量,工程监理,工程造价,开关电源,打包机,打码机,抛光机,报警器,探测器,接触器,提升机,文字,施工升降机,晶体管,智能制造,智能化,暖通,机器人,机械制造,机械加工,机械原理,机械工程,机械设备,机械设计,机电一体化,机电工程,林业,枪械,柴油发电机,柴油发电机组,柴油机,树脂瓦,桥式起重机,检测仪,检漏仪,模具钢,橡胶,步进电机,氙气灯,氩弧焊机,水利工程,水晶灯,波纹管,注塑机,清洗机,滚丝机,激光,激光切割机,激光打标机,灌装机,火焰切割机,火箭,焊接,焊机,煤矿,燃料油,环境工程,玻璃棉,玻璃钢,球磨机,生产线,生物医学,电力系统,电动机,电动车电池,电动门,电动阀门,电声工程,电子工程,电子技术研发,电工,电机,电气,电气自动化,电池电源开发,电烤箱,电热水器,电焊机,电磁炉,电线,电线电缆,电缆;环境学:噪声污染控制,固体废物处置,大气污染控制,水体污染控制,环境保护,环境学,环境污染,环境监测;心理学:强迫症,心理咨询,心理咨询师,心理学,心理障碍,情绪障碍,抑郁,抑郁症,教育心理学,焦虑,犯罪心理学,社会心理学,神经衰弱;职业教育:Android,CTA,HTML,HTML5,IOS,培训,Java,Linux,SEM,SEO,USCPA,一级建造师,中级工程师,二级建造师,云计算,人力资源管理师,会计从业考试,会计师,会计继续教育,会计考试,会计资格考试,公共营养师,军转干,函授,化妆培训,化妆学校,医学考试,厨师,叉车证,司法考试,国家公务员考试,学化妆,学历认证,学车,实训,导游证,工程师,平面设计培训,幼师,建筑师,执业医师考试,执业药师考试,技师证,招教,招教考试,教师培训,教师招聘,教师考试,教师职称,教师证,教师资格,时事热点,期货从业考试,汉浯,汽修培训,汽修教育,注册安全工程师,焊工证,特岗教师,电大,电工证,社区考试,精锐教育,综合知识,网站开发,网络工程,老师招聘,职业教育,职称,营养师,营钅肖师,行业英语,西点,计算机等级考试,证券从业考试,财会,财会培训,财经教育,财经题库,远程教育,选调生,造价工程师,造价师,金融风险管理,银行从业考试,银行招聘,高顿;升学入学:专升本,中考,保研,初中,春季高考,初中毕业,初中生,初中科学,初高中,升学入学,华中科技大学,华北电力大学,单招,单独招生,南昌大学,厦门大学,吉林大学,同等学力,在职考研,大学,天津高考,学习,学习软件,学前教育,宁波大学,对口升学,小学,小学生,崩坏学院,工程硕士,幼儿教师,成人高考,数理化,文凭,文综,毕业,毕业学分,江苏高考,注册入学,清华大学,燕山大学,理工学科,研究生,考博,考研英语,聊城大学,自主招生,自考本科,西安交通大学,论文代写,辽宁高考,郑州大学,院校信息,高中政治,高升专,高考志愿,高考英语;化学:元素周期表,初三化学,初中化学,化学,化学分析,化学方程式,化工车,固化剂,无机化学,有机化学,环氧,甲醛,聚乙烯,聚合氯化铝,聚四氟乙烯,聚氨脂,聚氨酯,高一化学,高中化学;外语学习:english,初中英语,初二英语,口语,听力,听力不好,商务英语,四六级,四级,外语学习,学习英语,学日语,德文,日本语,日语入门,日语学习,日语翻译,汉译英,翻译,背单词,英文,英文翻译,英语专八,英语专四,英语作文,英语六级,英语写作,英语单词,英语口译,英语口语,英语听力,英语四级,英语培训,英语学习,英语翻译,英语考试,英语词汇,语法,韩国语,韩语翻译;医学:临床医学,人工受精,儿科,免疫,内科,冰毒,冻疮,前列腺癌,化疗,医学,发育,吉非替尼,哮喘,唇炎,基础医学,外科,婴几护理,小叶增生,尿毒症,尿蛋白,心血管,性病科,护理学,支气管火,无精症,易瑞沙,白斑病,白细胞,睾丸,缩阴,羊癫疯,耳蜗,耳鸣,耳鼻喉,肉毒素,肛瘘,肠胃,肠道,股骨头,肺,胃痛,胃酸,胆囊炎,胆固醇,脊椎,脑中风,脑梗,脑瘫,脱髓鞘,腰椎,腰痛,腰间盘,腱鞘囊肿,腹泻,自闭症,艾滋,褥疮,西药,贫血,酒精肝,阴道,阿普唑仑,面瘫,颈椎,骨质疏松;语文:作文,修改病句,初中语文,古诗词,小学语文,成语,扩句,文言文,文言文翻译,比喻,议论文,语文,语文作文,语文教学,说明文,骈文,高中作文,高中语文;纺织:纺织,织绣;建筑学:建筑学,管道防腐,钢模板,钢管,钢结构,阳光房;出国留学:ACT,AP,GMAT,SSAT,出国留学,德国留学,护照,日本留学,留学申请,留学考试,留学费用;烹饪:东北菜,东南亚采,五谷杂粮,亚麻籽油,凉皮,包子,北京菜,华莱士,压面机,咖啡机,学烘焙,豕吊米,寿司,小吃快餐,巧克力,微波炉,徽菜,披萨,拉面,搅拌机,方太厨具,日本料理,月饼,果汁,水果,汉堡,汤口口,油烟机,洗碗机,海参,海鲜,消毒柜,淀粉,湘菜,火锅,灶具,炸鸡,点心,烤箱,烤肉,烤鱼,烧烤,烧饼,烹调,烹饪,煎饼,燃气灶,莴苣,玛卡,甜品,生鲜,砂锅,磨粉,粉条,糕点,素食,红枣,红糖,红薯,羊肉,腊肉,苦荞面,蛋挞,蛋糕,螃蟹,西兰花,西点,西餐,贝木,辣椒,酸辣粉,野外烧烤,铁板鱿鱼,闽菜,面包,面点,面筋粉,面粉,韩国料理,食品添加剂,饮食,饼干,馒头,鸡排,麻辣烫,黑暗料理;装饰:3M光学膜,3M商业标识,3M商用清洁,3M建筑装饰,3M电力接续,3M电子材料,3M省水阀,3M胶带,3M咼曼挂钩,PVC,不干胶,不锈钢,中空玻璃,乳胶漆,伸缩门,内墙涂料,吊灯,地暖管,地板装修,城市规划,塑钢门窗,墙板装修,外墙涂料,外墙漆,多彩涂料,天花吊顶,室内设计,建材,彩钢板,断桥铝门窗,木器漆,木门,松石,氟碳漆,水性涂料,水性漆,水电,沥月,淘宝装修,现货沥青,玻璃,玻璃幕墙,瓷砖胶,皮带,真石漆,石材,硅澡泥,窗户,管材,红木,纱窗,网店装修,胶水,自动门,艺术涂料,装修,装修涂料,装潢设计,装饰材料,钢化玻璃,钢材,钢筋,铝合金门窗,门窗,防腐涂料,集成吊顶;健身:丰苹果肌,保健养生,健身,减肥,劈叉,塑身,泳池,瘦身,跑步机,运动锻炼;宠物:宠物,宠物医生,宠物美容,宠物训练,水族,海水鱼,爬宠,猫,花鸟鱼虫,蛙类,蜘蛛,蜥蜴,训狗,龟;汽车:3M汽车,3M研磨,SLIV,专用车,丰田,二手车,五菱,保时捷,全球鹰,兰博基尼,冷藏车,凯迪拉克,别克,刹车片,加油车,劳斯莱斯,叉车,发动机,吉利,吉利帝豪,口曰,吊装车,名爵,垃圾车,堆高车,大众,奇瑞,奔驰,奥迪,宝来,宝马,宾利,山地车,布加迪,平行进口车,年检,广汽丰田,怠速抖动,悍马,房车,手动档,手拉车,指标拍卖,捷豹,捷达,排量,搅拌车,搬运车,摩托车维修,斯巴鲁,新能源汽车,新车,旅行车,昂科拉,本田,机动车辆保险,林肯,校车,梅赛德斯,欧宝,比亚迪,汉兰达,汽车,汽车保养,汽车养护,汽车内饰,汽车周边,汽车喷漆,汽车安全性能,汽车改装,汽车用品,汽车电器,汽车电子工程,汽车租赁,汽车空调,汽车结构工程,汽车维修,汽车装潢,汽车设计工程,汽车质量管理,汽车购买,汽车过户,汽车钥匙,汽车隔音,汽车零部件,汽车音响,汽车音响改装,油罐车,油耗,法拉利,洒水车,洗车,洗车机,派克,消防车,液压车,清障车,烧机油,牌照,牵引车,玛莎拉蒂,电动汽车,电瓶,电瓶车,福特,科鲁兹,积碳,米其林,红旗,自动档,自卸车,舞台车,英菲尼迪,荣威,行车记录仪,装载机,跑车,路虎,车主,车厢,车型,车标,车灯,车辆指标,车辆过户,轮胎,迈腾,进口车,通用;民俗:中秋节,传统文化,婚嫁,新年,春节,民俗,民俗传统,生活民俗,礼节礼仪,社会组织民俗,立午节,节日,节日民俗;交通:3M道路安全,uber,专车,亚航,交通事故,交通出行,交通运输,交通违章,优步,公交车,公路,公路自行车,动车,单车,地铁,坐飞机,大巴,大巴车,客机,安车,客运立占,平板车,平衡车,抢票,摩托,摩托车,春运,机场,民航,汽车时刻表,汽车示,汽车站,火车,火车票,电动滑板车,电动自行车,电动车,直通车,硬卧,硬座,站票,自行车,航班,虹桥机场,车牌,车牌号,车辆年检,软卧,违章,违章查询,邮轮,铁路,飞机,飞机票,首都机场,驾车路线,驾驶证,高铁;生活:3M净水,3M空气净化,ems,万宝路,三叶草,丰唇,二十四小时,交际,休闲食品,供暖,保健酒,保健食品,保养,内裤,冰柜,冰淇淋机,冰激凌,冷柜,净水器,净水机,加湿器,化妆,化妆品,十月妈咪,卷帘门,卸妆,厨具,厨卫,厨房,厨房设备,厨柜,反光面料,取暖器,煮止百,又少人,台风,吊坠,同城快递,吸顶灯,壁挂炉,外卖,外烟,大麦盒子,太阳能热水器,太阳能路灯,宝宝过敏,家用电器,家电,家纺,小吃车,山茶油,布料,干燥,干燥机,开水器,快递,快递查询,情趣内衣,户外污渍,打底裤,扫地机,扫地机器人,折叠车,推拉门,插座,新百伦,新雪丽,服饰,棚房,水果渍,水管,污水处理,油性,洗涤广品,活性炭,消防,潮牌,灯,灯光,灯具,灯带,灯饰,灵芝,烘干机,烟,烟机,热水器,燃气热水器,牛仔裤,牛奶,牛栏奶粉,特殊材质衣物清洗,特色小吃,生活,生活常识,申通,电器,电子电器维修,电源,电视,白袜,皇明太阳能,皮革保养,益生元,益生菌,矿泉水,社交,空气净化,调清洗,笑话,红茶,纯净水,绅士道,绿茶,缝纫机,羊绒,美体,羽绒,羽绒服;健康知识:三聚氰胺,亚硝酸盐中毒,健康咨询,农药残留,发烧,咳嗽,地沟油,地沟油检测,女性健康,感冒,敏感皮肤,水健康,痛经,皮肤病,腰疼,腿疼,苦荞茶,蛋白粉,视力,足贴,遗精,青汁,食品卫生,食品安全检测,食品安全法,食品质量,食品选购,食疗,黑苦荞茶;购物:O2O,bb霜,burberry,ebay,nike,prada,一元云购,京东,充气娃娃,全球购,卫浴,可乐机,商城,喷头,团购,土特产,天猫超市,家具,封口机,床,成人用品,服装,气狗,沙发,浴室柜,海外购,海尔,淘宝,淘宝刷单,淘宝卖家,淘宝推广,淘宝直通车,淘宝货源,淘宝购物,淘宝退款,渔昼,爱马仕,爽肤水,电水壶,眼镜,眼霜,粉底,粉底液,纽崔莱,纽巴伦,网上购物,网购,美国购物,美的,耐克,血糖仪,衣柜,购物,迪奥,退款,酒柜,阿迪,阿迪达斯,鞋柜,香奈儿,麦当劳;育儿:催乳,奶粉,学前孝攵育,宝宝性别,月嫂,母婴,米粉,育几,进口奶粉;球类运动:NBA,乒乓,乒乓球,乔丹,亚冠,保龄球,切尔西,上土,巴萨,投篮,排球,斯诺克,曲棍球,梅西,棒球,橄榄球,湖人,爱好篮球,爱好足球,球类运动,球鞋,皇家马德里,科比,篮球,篮球鞋,网球,羽毛球,羽毛球拍,足球,足球鞋,马刺,咼尔夫,高尔夫球;冰雪运动:冰雪运动,无舵雪橇,有舵雪橇,滑雪,花样滑冰;足球运动:世界杯,中超,南美解放者杯,德甲,意甲,欧冠,欧洲杯,联合会杯,英超,葡甲,西甲,足球赛事,足球运动,非洲国家杯;智力运动:中国象棋,五子棋,军棋,围棋,国际象棋,国际跳棋,扑克,智力运动,桥牌,蒙古象棋,象棋,跳棋,麻将;武术:功夫,咏春拳,太极,太极拳,截拳道,拳击,散打,柔道,武术,气功,泰拳,空手道,跆拳道,马术;体育运动:体操,体育器材,体育用品,体育设施,体育运动,健身,公路车,哑铃,弹跳,户外运动,拓展训练,游泳,游泳馆,滑板,田径,肌肉,跑步,跑酷,运动鞋,长跑,飙车,马拉松;体育赛事:CBA,亚运会,伦敦奥运,体育赛事,冬奥会,冬季两项,北欧两项,奥运会;绘画:人物画,国画,壁画,山水画,手绘,插画,水彩,水彩画,水粉画,油画,版画,画室,画师,画画,硕思go设计师,简笔画,素描画,连环画;工艺品:冰晶画,和田玉,工艺品,根雕,民间工艺,民间艺术,玉,玉石,玉雕艺术,玛瑙,玩具,琉璃,琥珀,石雕,紫砂艺术,红木艺术,蓝宝石,金镶玉,陶瓷艺术,雕刻,黄花梨,黑曜石;历史:世界历史,历史,历史学,唐朝,孔子,宋朝,战国,抗战,文革,汉朝,清宫图,考古,考古学,高中历史;视觉设计:3D动画,PS,logo设计,修图,包装设计,单反相机,单反镜头,图像处理,图片处理,平面设计,广告设计,投影机,抠图,摄影器材,效果图,标志设计,画册设计,旦旦,美图秀秀,胶卷,艺术概念设计,装潢设计,视觉设计;文学:三生三世十里桃花,东方不败,中国文学,云中歌,亚索,任我行,作诗,傲世九重天,儿童文学,全文,全本,写作,冰与火之歌,历史小说,古代文学,古典文学,古文,古诗,古诗词,古风,古龙,同人小说,名著,哈利波特,唐人街探案,唐家三少,四大名著,国学,外国文学,大秦帝国,女生小说,宋词,对联,小说,庄子,快穿文,怦然星动,恐怖小说,惊悚乐园,戏剧,我的贴身校花,打油诗,推荐小说,散文,文学,文言文,文集,斗破苍穹,春秋战国,曲艺,权利的游戏,校园小说,校花的贴身高手,核心期刊,武动乾坤,民间文学,水浒,水浒传,沉香,浮生物语,灵异小说,玄幻小说,现代,番外,白殿疯,神话故事,穿越小说,繁体,红楼,红楼梦,绘图,网游小说,网络小说,耳雅,耽美小说,莽荒纪,藏尾诗,西楚霸王,西游,西游记,占情小说,诗人,诗经,诗词,路明非,轻小说,道德经,都市小说,金庸,鬼吹灯,黄梅戏;时尚:丝袜,丰太阳穴,丰胸,光子嫩肤,养发,出油,包包,化妆,化妆品,半永久化妆,卧蚕,去痘,去皱,去眼袋,双眼皮,双眼皮修复,发型,发际线,煮兰,吸脂,埋线,埋线减肥,头发护理,女包,女装,妊娠斑,学美发,子合,学美甲,开眼角,形象设计,彩妆,微整形,手工,护发,护肤,护肤品,抽脂,拉皮,整形,整形医院,整形美容,斑点,时尚,时尚买手,服装,服装设计,服饰,染发,植发,植眉,毛孔粗大,水光针,汉服,浪琴,激素脸,玻尿酸,珠宝设计,田丬士,力衣,痘印,痘坑,瘦脸,瘦脸针,瘦腿,皮具,皮肤干燥,皮肤松弛,皮肤问题,皮鞋,皱纹,眼袋,磨骨,祛斑,祛痘,祛痘印,祛皱,祛眼袋,种头发,科颜氏,粉刺,精油,紧身,紧身裤,红血丝,纹眉,细纹,缺水,美人尖,美发,美妆,兰六,美容仪器,美容养颜,美容培训,容学忄交,美容师,美容护肤,美容整形,美容美发,美谷院,美甲,美白,美白针,美瞳,老年斑,胶原蛋白,脂肪填充,脂肪移植,脂肪粒,脱发,脱毛,脱脂,脸过敏,自体脂肪,色斑,补水,裤袜,角质层,连裤袜,防晒,除皱,隆胸,雀斑,雅诗兰黛,青春痘;书法:书法,字画,小篆,楷书,篆书,草书,行书,隶书;艺术:喜剧,国家大剧院,大剧院购票,手机购票,文学艺术,歌剧,舞蹈,艺术,艺术字,购票;数码:3M防窥,3d打印,3d打印机,CPU,GBA,HTC,LG,MOTO手机,MP3,MP4,Mate9,NDS,OPPO,P9,PSP,TCL,U盘,Wii,5pro,Xbox,ZUK手机,apple,coolpad,flyme,iPad,iPhone,iPod,iphone5,iphone5s,iphone6,iphone6plus,iphone6s,iphone6splus,iphone7,iphones,iPhone换屏,kindle,lumia,mate7,mate8,moto,mx4,mx5,nokia,note2,note3,note5,nubia,OPPOr,r7s,root权限,SD卡,Sony,surface,uv打印机,VIVO,VivoX,vivoX3,vivoX5,vivoxplay,VivoShot,vivo电池,vivoY37,VIVOX,vivox3,vivox5,vivoxplay,Vivo官网,vivo手机,windowsphone,xperia,z9,二星s3,二星s4,二星s5,二星s6,二星s6edge,三星手机,东芝,中兴手机,中央空调,主板,乐服务,乐视手机,乐视电视,二手手机,优盘,传真机,佳能,修机机,儿童手表,充电器,充电宝,内存,内存卡,冰箱,创维电视,刷机,刻字机,努比亚,努比亚手机,华为,华为手机,华为荣耀,单反,单片机,喷墨打印机,坚果手机,垃圾短信,塞班,复印机,天语手机,奇酷手机,安卓系统,家用电器研发,小天才早教机,小米,小米3,小米4,小米note,小米手机,小米盒子,平板,平板打印机,平板电脑,应用下载,录像机,微鲸电视;智能设备:云麦好轻,体脂称,体重秤,可穿戴设备,定位手表,智能仪器,智能体重秤,智能卡,智能家居,智能手表,智能插座,智能眼镜,智能硬件,硬件,空气净化器;电脑:CPU,Mac,macbook,mathtype,thinkpad,thinkpad笔记本,usb,一体机,二星笔记本,上网,东芝笔记本,主机,主板,交换机,光盘,关机,分区,办公软件,华硕,华硕笔记本,卸载,双系统,台式机,台式电脑,名片设计,启动,固态硬盘,处理器,外设,多线程,宏基笔记本,应用,应用软件,开机,戴尔笔记本,文件恢复,无线网,无线网络,机器,机房,机械键盘,核芯显卡,死机,水印,漏洞修复,电脑,电脑中毒,电脑主板,电脑加速,电脑开机,电脑故障,电脑死机,电脑电源,电脑病毒,电脑系统,电脑维护,电脑蓝屏,电脑装机,电脑配置,电脑重装,电脑问题,硒鼓,硬件,神舟笔记本,笔记本,系统,系统故障,系统重装,索尼笔记本,组装机,组装电脑,网线,网络,网络故障,网速,联想笔记本,芯片,苹果电脑,苹果笔记本,装机,装系统,越狱,路由器,软件,选购,配置,酷睿,重启,重装,重装系统,键盘,隐私,鼠标;软件应用:360安全卫士,360安全浏览器,360手机卫士,360浏览器,3DMAX,3dsmax,ACDSee,AfterEffects,C4d,Cimatron,Flash,IOS,Illustrator,MAYA,Photoshop,Premiere,QQ保护,QQ加速,WPS,access,adobe,cad,centos,chrome,coreldraw,CSS3,debian,discuz,dropbox,excel,execl,firefox,foxmail,gmail,iOS,iWork,icloud,itunes,Latex,linux,mindmanager,office,onenote,outlook,powerpoint,ppt,pptv,qq浏览器,redhat,Skype,ubuntu,uc浏览器,LIPS,u盘装系统,visualbasic,w币管理,win10,Win7,Win8,word,wordpress,xmind,YY,root,万能钥匙,个人中心,云盘,人人贷,代理软件,免费电话,全民k歌,分期乐,刷票,刷钻,加密软件,动画制作,去水印,垃圾清理,墙软件,多媒体软件,大白菜,安全短信,安卓,宜人贷,宜信,应用宝,微星,快捷中心,快播,思维导图,悬浮囱,手机加速,手机浏览器,手机管家,手机系统,手机防盗,拍拍贷,操作系统,收银软件,改图,施工软件,服务器软件,杀毒软件,注册,浏览器,清理加速,滴滴,激活,火狐浏览器,瑞星,用友,用友软件,电脑杀毒,病毒,病毒查杀,短信平台,破解软件,社交软件,私密空间,管理系统,精雕,系统激活,组态软件,网游加速器,网络电话,美团,美拍;互联网:115网盘,360,360云,360云盘,360推广,QQ,QQ空间,WIFI,app支付,e租宝,facebook,google,instagram,p2p平台,twitter,wifi万能钥匙,wifi密码,yahoo,youtube,乐视盒子,云购,互联网,互联网资源,亚马逊,优分期,优酷,光纤宽带,目刂而,华为,宽带连接,局域网,微信,微信支付,微信营销,微博,微店,微来购,微盘,微营销,推特,搜狐,搜狗,搜索引擎,数据分析,新浪,新浪微博,无线网卡,淘宝网,电子商务,白条,百度推广,百科,移动互联网,网易云音乐,网瘾,网站,网站优化,网站模版,网站系统,网络加速器,网络推广,网络营销,网络连接,美国亚马逊,翻墙,联想,脸书,腾讯,腾讯地图,论坛,豆瓣,邮箱,阿里;通讯技术:3G业务,3M通讯接续,4G业务,中国移动,光纤,苋带业力,对讲机,手机,无线上网,电信,电信套餐,电信宽带,电信手机,电信流量,电信网络,移动,移动通信,米柚,群发短信,联通,话费,通信电源,通讯技术,通讯服务;IT:.net,ACP,APK,AR,App,AutoCAD,C++,CSS,C语言,Delphi,Dreamweaver,ERP,Eclipse,Flash设计,Fortran,HTML,Hadoop,Intel,JSP,Javascript,Jquery,MASTERCAM,NetBeans,Oracle,PHP,Pascal,Perl,Python,Ruby,SQL,UG,VC++,VisualBasic,android,android开发,apache,arcgs,asp,awk,bios,CISSP,dos,gpu,htm15,https,ios开发,Java,javaweb,matlab,mfc,myeclipse,mysql,nglnx,nodejs,prince2,redis,sed,shell,sqlserver,ssd,struts,thinkphp,VC,Vim,vmware,w7,web前立,web开发,web服务器,Win,win8.1,windows,windowsl0,windows7,windows8,Winxp,wp10,wp8,xml,XP,XP系统,云服务,云计算,互联网产品设计,交互设计,前端开发,加密,听不清,多媒体开发,大数据,嵌入式,嵌入式硬件开发,嵌入式软件开发,建站,开发板,手机应用开发,手机软件开发,搜索引擎优化,数据库,数据恢复,数据结构,易语言,服务器,本地连接,架构设计,汇编语言,游戏开发,游戏界面,游戏界面设计,物联网,电子软件开发,电脑硬件设计,移动开发,算法,编程,编程语言,网站架构设计,网络信息安全,网络运营,网页设计,自助建站,英特尔酷睿;烦恼:交友,交友技巧,他来了请闭眼,借钱,同事关系,处女膜,奇葩说,心理咨询,性心理,情感,敏感,烦恼,爱上哥们,结婚证,自闭,虐待,青春期;爱情:分手,初恋,同性恋,恋爱,情侣,感情,暗恋,爱情,表白,追求;家庭关系:出轨,婚姻,家庭关系,怀孕,相亲,离婚,结婚;两性问题:两性问题,做爱,安全期,性交,性爱,性生活,月经,友友,自慰;校园生活:校园,校园生活;游戏:3d坦克,4399,QQ三国,QQ仙侠传,QQ仙境,QQ华夏,QQ堂,QQ宠物,QQ封神记,QQ幻想,QQ幻想世界,QQ游戏,QQ炫舞,QQ自由幻想,QQ西游,QQ音速,QQ飞行岛,QQ飞车,QQ魔域,cf手游,CS,dnf,dota,dota2,fc游戏,gta5,ios游戏,minecraft,PPSSPP,ps2,ps3,ps4,ps游戏,qq水浒,war3,xbox360,万王之王,三国志11,三国志12,三国志13,三国战记,三国无双,二国杀,三国群英传,上古卷轴,上古卷轴5,丝路英雄,丧尸围城,中华英雄,主机游戏,九游,九游游戏,乙女游戏,九阴真经,九鼎传说,乱斗西游,乱斗西游2,亚人,仙剑,仙剑,仙剑奇侠传,仙途ol,代练,任天堂,传奇,传奇世界,传奇外传,传奇私服,伪装者,使命召唤,使命召唤,使命召唤online,侠客风云,侠客风云传,侠盗猎车,侠盗猎车手,侠盗飞车,信长之野望,光宇游戏,全员加速中,全民枪战,全民突击,全民飞机大战,全面战争,冒险岛,军团要塞2,冰封王座,冰雪奇缘,冷兵器,刀剑神域,刀剑英雄,刀塔,刀塔传奇,创世西游,刺客信条,剑3,剑仙,剑侠世界,剑侠情缘,剑网3,剑网三,剑魂之刃,功夫小子,动作游戏,劲舞团,勇者大冒险,半条命,单机游戏,卡特琳娜,卡通农场,双环,反恐精英,反恐精英,反恐行动,口袋妖怪,口袋怪兽,口袋西游,古剑奇谭,古域,古墓丽影,古墓丽影9,吞食天地,售货机,噬神者;博彩:七乐彩,七星彩,买彩票,体彩,体育彩票,博彩,双色球,同花顺,大乐透,彩票,时时彩,福利彩票,福彩,赌钱,足彩,重庆时时彩;收藏:书画,光绪元宝,古币,古玩,古琴,古籍,古钱,古钱币,奇石,宝石,收藏,武器,满文,猎枪,玉器,玉镯,珠宝,琳琅,甲骨,石刻,硬币,碑帖拓本,竹雕,符牌印章,红宝石,纪念币,纸币,艺术品,象牙雕刻,邮币,金银器,钱币,铜器,银元,银币,错币,错版,陶器;旅游:出境游,北京旅游,华山,厦门旅游,台湾旅游,唐人街,四川旅游,国内旅游,国内游,国际机票,定制旅游,导游证,巴厘岛,度假,度假旅游,往返机票,成都旅游,携程,旅游,旅游住宿,旅游攻略,旅游签证,旅行社,日本旅游,泰国旅游,海外旅游,游艇,游轮,特产,纪念碑谷,美国旅游,自助游,自由行,自驾游,西藏旅游,酒店,韩国旅游,香港,香港旅游,马尔代夫,马来西亚,鼓浪屿;占卜:五行,八字,八字算命,六壬,占卜,双子座,双鱼座,周公解梦,周易,塔罗,塔罗牌,处女座,天秤座,天蝎,天蝎座,奇门遁甲,射手座,巨蟹座,手相,摩羯座,易经,星座,星座运势,水瓶座,狮子座,玄学,生辰八字,白羊座,算卦,算命,血型,解梦,起名,金牛座,风水;动漫:Cosplay,七龙珠,什么动漫,假面骑士,偷星九月天,光之美少女,全职猎人,动漫原图,动漫同人,动漫图,动漫图片,动漫头像,动漫歌曲,动漫游戏,动漫音乐,动画,动画片,原画,变形金刚,叛逆的鲁鲁修,叶罗丽精灵梦,同人漫画,名侦探柯南,吸血鬼骑士,命运石之门,哆啦a梦,圣斗士,地狱少女,多啦a梦,头文字d,奥特曼,妖精的尾巴,宅舞,宠物小精灵,布袋戏,恶魔咼校,战舰少女,日本动漫,日本漫画,暗杀教室,暴№旦,机器猫,歌之王子殿下,正义联盟,死亡笔记,海贼王,灌篮高手,火影同人,火影忍者,热血高校,神兵小将,秦时明月,精灵旅社,终结的炽天使,网球王子,美少女战士,耽美动漫,耽美漫画,蔷薇少女,蜡笔小新,螺旋猫,轻音少女,辛普森,进击的巨人,铠甲勇士,零之使魔,风车动漫,飒漫旦,魔卡少女樱,魔法少女,魔法少女小圆,黑塔利亚,黑子的篮球,龙王传说,龙珠;影视:3D,一吻定情,中国好声音,乡村爱情,亚瑟王,倚天屠龙记,倩女幽魂,功夫熊猫,功夫熊猫3,加勒比海盗,动作电影,动画形象设计,十二道锋味,印广电蟛,古装剧,古装电视剧,叶问,后期制作,吸血鬼日记,复仇者联盟,夏洛特烦恼,外国电影,大秧歌,天涯明月刀,太子妃升职记,太阳的后裔,央视,奇酷,奔跑吧兄弟,奥斯卡,女医明妃传,奶酪陷阱,好莱坞,寻龙诀,射雕英雄传,小时代,少帅,康熙来了,影视,影视评论,恐怖电景,恶作剧之吻,恶棍天使,我的少女时代,指环王,捉妖记,搞笑,摄像,播音主持,旋风少女,无心法师,无限挑战,日剧,日本电影,星光大道,星动亚洲,星球大战,星际迷航,暗黑者,暮光之城,最强大脑,最新电影,权力的游戏,来自星星的你,极限挑战,欧美电影,泰剧,湖南卫视,澳门风云,澳门风云3,火星救援,灵魂摆渡,煎饼侠,熹妃传,爱情公寓,爸爸去哪几,王牌对王牌,班淑传奇,琅琊榜,甄传,生死狙击,电影介绍,电影资源,电视剧,电锯惊魂,盗墓笔记,神探夏洛克,神探狄仁杰,神秘代码,科幻片,笑傲江湖,终极一班,终极三国,经曲电影,综艺节目,编剧制片,网络剧,美丽的秘密,美剧,美国电影,美国队长,老友记,老炮几,老电影,耽美电影,聊斋,舌尖上的中国,芈月,芈月传,花干骨,英剧,荒野猎人,蜘蛛侠,蝙蝠侠,表演,视频,豫剧,连续剧,速度与激情,邪恶力量,重案六组,金星秀,钢铁侠,闪电侠,阿凡达,霍比特人;明星:beyond,hebe,s.h.e,Selina,shinee,snh48,tfboys,东方神起,主题曲,久石让,五月天,任家萱,何炅,光洙,凤凰传奇,刘亦菲,刘在石,刘德华,刘诗诗,华人明星,吴世勋,吴亦凡,天石石,周星驰,周杰伦,周笔畅,唐嫣,大陆明星,宋智孝,宋茜,少女时代,少年包青天,崔胜贤,张信哲,张卫健,张国荣,张学友,张杰,张碧晨,张翰,张乙兴,张贤胜,张韶涵,成龙,我是歌手,新垣结衣,日本明星,日本综艺,日韩明星,明星,易烊干玺,朴信惠,朴妮唛,朴孝敏,朴智妍,朴有天,朴灿烈,权志龙,李健,李光洙,李准基,李宇春,李小龙,李弘基,李易峰,李胜贤,李钟硕,杨幂,杨洋,杨蓉,杨颖,杰克逊,林俊杰,林允儿,林夕,梁静茹,欧美明星,毕福剑,波多野结衣,港台明星,王俊凯,王凯,王力宏,王嘉尔,王源,王菲,田馥甄,网络红人,胡歌,苏打绿,许嵩,许巍,许晴,谢娜,赵丽颖,边伯贤,迈克尔杰克逊,邓紫棋,郑允浩,郑多燕,郑容和,郑恺,郑爽,郑秀妍,郭德纲,郭敬明,金俊勉,金俊秀,金南俊,金希澈,金明洙,金泰妍,金秀贤,金钟大,钟汉良,阿信,陈乔恩,陈伟霆,陈冠希,陈嘉桦,陈奕迅,陈子豪,陈学冬,陈晓,陈赫,霍建华,靳东;舞蹈:国标舞,广场舞,拉丁舞,摩登舞,民族舞,爵士舞,现代舞,舞蹈,巴茔,街舞,鬼步舞;音乐:演奏,线谱,响乐,滴歌神啊,生态音乐,典音乐,风歌,旧声,大剧院,室内乐,戏曲,新歌,无损音乐,日文歌,日本音乐,日韩歌曲,欧美歌曲,欧美音乐,歌曲,歌词,民乐,民谣,求歌名,流行歌曲,流行音乐,港台歌曲,点歌机,神曲,类似爱情,纯音乐,经典老歌,绝世唐门,编曲,网络歌手,网络歌曲,老歌,月景音乐,舞曲,英文歌,英文歌曲,莫扎特,视频音乐,话剧,贝多芬,轻音乐,配乐,钢琴曲,韩国歌曲,韩国音乐,韩文歌,韩文歌曲,音乐,音乐会,音乐制作,音乐节,音频怪物;品鉴:佛珠,咖啡,啤酒,巧克力,干红,水晶,火腿,白酒,红酒,美食,茶叶,葡萄酒,进口红酒,酒水,酒水饮料,钻石,餐饮酒店,香水;娱乐:中国新歌声,中奖,台钓,垂钓,天天向上,娱乐,幽默搞笑,幽默笑话,快乐大本营,捕鱼,放假,春晚,段子,相声,看片,脑筋急转弯,萌宠,蒙面歌王,谜语,赌场,韩国娱乐,鱼线,鱼饵;乐器:乐器,口琴,古典吉他,古筝,吉他,吉他谱,大提琴,小提琴,手风琴,架子鼓,海伦钢琴,珠江钢琴,琴行,电吉他,电子琴,竖琴,竹笛,笛子,萨克斯,葫芦丝,贝斯,钢琴,钢琴谱,长笛;医疗:3M医疗产品,不孕不育,人工耳蜗,便秘,制氧机,医疗,医疗设备,春药,曲马多,狂犬病,甲亢,男科,癫痫,矫正,胃胀,腰肌劳损,腹泻,颈椎病,骨刺,骨质增生;健康:3M皮肤护理,3M食品安全,低聚果糖,保健品,健康,失眠,弱精,戒烟,手淫,护多乐,排毒,晒斑,男性健康,耐适康,脂溢性脱发,补肾;妇产科:不孕,不育,习惯性流产,乳腺,乳腺囊肿,乳腺增生,乳腺炎,乳腺疾病,产后,产后恢复,例假,卵巢,卵巢囊肿,卵巢早衰,堕胎,多囊,大姨妈,妇产科,妇科炎症,妇科疾病,妇科病,妊娠纹,子宫肌瘤,宫颈,宫颈糜烂,性别鉴定,房事,打胎,排卵,排卵期,无痛人流,月经不调,月经推迟,白带,盆腔炎,盆腔积液,胎儿性别,试管婴几,输卵管,闭经,阴道炎,霉菌性阴道炎,预产期;皮肤科:克炎王,带状疱疹,扁平苔藓,手癣,毛囊炎,灰指甲,牛皮癣,生殖器疱疹,疤痕,痘,痤疮,白癜风,白癫风,白颠疯,皮炎,皮肤科,皮肤过敏,股癣,脂溢性皮炎,脚臭,腋臭,花粉过敏,过敏,银屑病,鱼鳞病;中医:中医,中草药,中药材,丹参,月子病,耳背,肾虚,肾阴虚,药材,药酒,阳痿早泄,阿胶,阿胶糕,风湿,黑枸杞;五官科:3M齿科修复,中耳,中耳炎,五官科,儿童助听器,口腔,口腔溃疡,口腔科,口臭,听力下降,听力损失,听觉,咽炎,弱视,拔牙,散光,烤瓷牙,牙套,牙疼,牙科,牙齿矫正,牙齿美白,白内障,眼睛,眼科,神经性耳聋,种植牙,老人助听器,耳朵,耳聋,耳道,耳鸣,耳鼻喉科,蛀牙,补牙,角膜塑形镜,过敏性鼻炎,近视,近视手术,近视眼,远视,镶牙,青光眼,高度近视,鼓膜;儿科:儿科,宝宝感冒,惊厥;内分泌科:内分泌科;内科:三叉神经痛,丙肝,内分泌失调,内科,冠心病,呼吸内科,心脏病,心血管内科,性病,抽动症,拉肚子,消化内科,甲状腺,痛风,癫痫病,白血病,神经内科,大风冫显,糖尿病,肠炎,肩周炎,肺气肿,肺炎,肺癌,肾内科,肾病,肾结石,肾衰竭,胃病,脂肪肝,脊髓炎,脑血栓,腱鞘炎,血液科,透析,风湿科,高血压,高血脂;肿瘤科:乳腺癌,放疗,淋巴癌,特罗凯,癌症,肿瘤,肿瘤科,血管瘤,食道癌,骨髓瘤;传染科:hiv,乙肝,传染,传染病,传染科,大三阳,小三阳,尖锐湿疣,梅毒,艾滋病;人体常识:乳房,人体常识,更年期;男性泌尿科:前列腺,前列腺炎,勃起,包皮,包皮手术,包皮过长,壮阳,早泄,泌尿,男性泌尿科,阳痿;精神心理科:安眠药,精神分裂,精神心理科,精神病;外科:关节炎,外科,毛发移植,泌尿外科,精索静脉曲张,肛肠外科,肝胆外科,股骨头坏死,脑外科,腰椎病,腰椎间盘突出,腰间盘突出,骨折,骨科";
    private String[] allInterests = Interests.split(";");
    private HashMap<String, String> interestsMap = new HashMap<>();
    private ArrayList<String> data2 = new ArrayList<>(), data3 = new ArrayList<>();//分别表示加载到二级、三级的ListView中的数据
    private int firstIndex = 0, secondIndex = 0;//data_level_2的一、二级索引，用于定位二级兴趣，用于查找三级兴趣

    private ListView level_1, level_2, level_3;
    private ArrayAdapter<String> adapter_2, adapter_1, adapter_3;
    private View one, two;//one用于保存一级ListView中之前选择的项目的View,two用于保存二级ListView中之前选择的项目的View

    private ArrayList<String> interests = new ArrayList<>();//用户选择的所有兴趣
    private LinearLayout linearLayout_interests;
    //private TextView results;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_interests);
        ActivityCollector.activities.add(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_interests);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initInterestsMap();

        //results = (TextView) findViewById(R.id.interests_results);
        linearLayout_interests = (LinearLayout) findViewById(R.id.linearlayout_interests);
        level_1 = (ListView) findViewById(R.id.first_level);
        adapter_1 = new ArrayAdapter<String>(SelectInterests.this, android.R.layout.simple_list_item_1, data_level_1);
        level_1.setAdapter(adapter_1);
        level_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                firstIndex = position;
                //清空三级ListView数据，还原到初始界面
                data3.clear();
                adapter_3.notifyDataSetChanged();
                if (two != null) {
                    two.setBackgroundColor(Color.parseColor("#eeeeee"));
                    two = null;
                    level_3.setBackgroundColor(Color.parseColor("#dcdcdc"));
                }
                //清空并加载二级ListView数据
                data2.clear();
                for (int i = 0; i < data_level_2[position].length; i++) {
                    data2.add(data_level_2[position][i]);
                }
                adapter_2.notifyDataSetChanged();
                level_2.setBackgroundColor(Color.parseColor("#eeeeee"));
                //判断当前选择的项目是否是之前选择的，如果不是，设置为选中效果
                if (one != view) {
                    if (one != null)
                        one.setBackgroundColor(Color.parseColor("#dcdcdc"));
                    view.setBackgroundColor(Color.parseColor("#eeeeee"));
                    one = view;
                }
            }
        });

        level_2 = (ListView) findViewById(R.id.second_level);
        adapter_2 = new ArrayAdapter<String>(SelectInterests.this, android.R.layout.simple_list_item_1, data2);
        level_2.setAdapter(adapter_2);
        level_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                secondIndex = position;
                data3.clear();
                String value = interestsMap.get(data_level_2[firstIndex][secondIndex]);
                String[] temp = value.split(",");
                for (int i = 0; i < temp.length; i++)
                    data3.add(temp[i]);
                adapter_3.notifyDataSetChanged();
                if (two != view) {
                    if (two != null)
                        two.setBackgroundColor(Color.parseColor("#eeeeee"));
                    view.setBackgroundColor(Color.parseColor("#ffffff"));
                    two = view;
                }
                level_3.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        level_3 = (ListView) findViewById(R.id.third_level);
        adapter_3 = new ArrayAdapter<String>(SelectInterests.this, android.R.layout.simple_list_item_1, data3);
        level_3.setAdapter(adapter_3);
        level_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = data3.get(position);
                int index = isContained(s);
                if (index == -1) {
                    interests.add(s);
                    updateResultView();
                } else
                    Toast.makeText(SelectInterests.this, "你已选择过！", Toast.LENGTH_SHORT).show();


                //在interests中未找到选择的项目，则加入interests并在结果TextView中显示
                /*if (index == -1) {
                    interests.add(s);
                    if (results.getText().toString().equals("可多选"))
                        results.setText(s);
                    else
                        results.setText(results.getText().toString() + "，" + s);
                } else {//在interests中找到选择的项目，则从interests中移除并在将结果TextView更新
                    interests.remove(index);
                    if (interests.size() == 0)
                        results.setText("可多选");
                    else {
                        String t = "";
                        for (String item : interests)
                            t = t + "，" + item;
                        results.setText(t.substring(1));
                    }
                }*/
            }
        });
    }

    private void updateResultView() {
        linearLayout_interests.removeAllViewsInLayout();
        int rows = (interests.size() % 3 == 0) ? (interests.size() / 3) : (interests.size() / 3 + 1);
        LinearLayout[] linear = new LinearLayout[rows];
        for (int i = 0; i < rows; i++) {
            linear[i] = new LinearLayout(getApplicationContext());
            linear[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linear[i].setOrientation(LinearLayout.HORIZONTAL);
        }
        int index = 0;
        for (int i = 0; i < interests.size(); i++) {
            TextView temp = new TextView(getApplicationContext());
            LinearLayout.LayoutParams tparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tparam.setMargins(5, 5, 5, 5);
            tparam.weight=1;
            temp.setLayoutParams(tparam);
            temp.setText(interests.get(i));
            temp.setGravity(CENTER);
            temp.setTextColor(Color.parseColor("#000000"));
            temp.setBackgroundResource(R.drawable.rounded_rect_bg);
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = ((TextView) v).getText().toString();
                    interests.remove(isContained(s));
                    v.setVisibility(View.GONE);
                }
            });
            linear[index].addView(temp);
            if ((i + 1) % 3 == 0) {
                index++;
            }
        }
        for (int i = 0; i < rows; i++) {
            linearLayout_interests.addView(linear[i]);
        }
    }

    /**
     * 初始化三级兴趣HashMap
     */
    private void initInterestsMap() {
        String[] temp;
        for (int i = 0; i < allInterests.length; i++) {
            temp = allInterests[i].split(":");
            interestsMap.put(temp[0], temp[1]);
        }
    }

    /**
     * 在interests中寻找s，找到返回下标，否则返回-1
     *
     * @param s 要查找的的兴趣
     * @return 返回查找结果
     */
    private int isContained(String s) {
        for (int i = 0; i < interests.size(); i++) {
            if (interests.get(i).equals(s))
                return i;
        }
        return -1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
