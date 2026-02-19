import { useSearchParams } from "react-router-dom";
import Container from "../components/layout/Container";
import Footer from "../components/layout/Footer";
import Navbar from "../components/layout/Navbar";
import ArticleList from "../components/news/ArticleList";
import useNews from "../hooks/useNews";
import Pagination from "../components/news/Pagination";

export default function HomePage() {
  // const mockArticles = [
  //   {
  //     link: "https://www.hindustantimes.com/cricket/bcb-wants-to-repair-bcci-ties-post-t20-world-cup-turmoil-diplomatic-row-denied-bangladesh-participation-101771475841665.html",
  //     title:
  //       "BCB wants to repair BCCI ties post T20 World Cup turmoil: Diplomatic row denied Bangladesh participation",
  //     description:
  //       "Bangladesh’s State Minister for Youth and Sports Aminul Haque clarified that unresolved diplomatic issues resulted in T20 World Cup non-participation",
  //     content:
  //       "Subscribe Now! Get features like\n\nBangladesh’s newly appointed State Minister for Youth and Sports, Aminul Haque, has indicated he is keen to reset cricketing ties between the Bangladesh Cricket Board (BCB) and the Board of Control for Cricket in India (BCCI) and “resolve the issue quickly” following the events that led to Bangladesh’s withdrawal from the T20 World Cup 2026.\n\n“After taking the oath today, I met the Deputy High Commissioner of India at the Parliament building. I discussed the T20 World Cup with him,” Haque said on Tuesday after Bangladesh’s new cabinet was sworn in following the general elections. “It was a cordial conversation. I told him that we want to resolve this issue quickly through discussions because we want to maintain friendly relations with all our neighbouring countries.”\n\nALSO READ: Imad Wasim’s ex-wife shares painful, heartbreaking WhatsApp messages from Pakistani cricketer, seeks Mohsin Naqvi’s help\n\nTensions between the two cricket boards, who are set to co-host the 2031 World Cup, escalated after fast bowler Mustafizur Rahman, the only Bangladeshi in IPL 2026, was removed from the Kolkata Knight Riders squad. Bangladesh, citing security concerns, then urged the ICC to shift their World Cup matches out of India. The ICC rejected the plea and later replaced Bangladesh in the tournament with Scotland.\n\nWhile Bangladesh’s sports advisor Asif Nazrul blamed both the players and the BCB for the team’s absence from the tournament, Haque clarified that unresolved diplomatic issues were at the heart of the matter.\n\n“You know that because of diplomatic complications, we could not play in the World Cup. If those issues had been discussed and settled earlier, our team might have participated,” he said.\n\nThe ICC, however, assured that Bangladesh would not face penalties for refusing to travel to India and promised the country hosting rights for a global tournament in the 2028–31 cycle.\n\nHaque’s statement could indicate that if relations improve, India’s scheduled tour of Bangladesh in September, comprising three ODIs and three T20Is, may proceed as planned.",
  //     pubDate: "2026-02-19T04:58:44.000+00:00",
  //     category: "cricket",
  //     source: "hindustantimes.com",
  //     summary:
  //       "Bangladesh's new State Minister for Youth and Sports, Aminul Haque, expressed a desire to resolve cricketing ties with India after Bangladesh's withdrawal from the T20 World Cup 2026. Haque met with the Deputy High Commissioner of India to discuss the issue, describing the conversation as cordial. He stated that Bangladesh wants to maintain friendly relations with neighboring countries and resolve the issue quickly through discussions. The tensions between the two cricket boards escalated due to security concerns and unresolved diplomatic issues. Haque's statement may indicate that India's scheduled tour of Bangladesh in September could proceed as planned if relations improve.",
  //     retryCount: 0,
  //     lastSummaryAttemptAt: "2026-02-19T05:05:50.193Z",
  //   },
  //   {
  //     link: "https://indianexpress.com/article/world/iran-us-conflict-trump-military-strike-date-10540041/",
  //     title:
  //       "US ‘Weekend strike’ against Iran on the cards, but Trump yet to decide",
  //     description: "",
  //     content:
  //       "The rapid buildup of US military forces in the Middle East has reached a point where President Donald Trump could approve strikes against Iran soon. In fact, the strikes could come as early as this weekend, according to administration and Pentagon officials, reported The New York Times.\n\nThe United States has continued moving military equipment and personnel into position that could be used to target Iran’s nuclear facilities, ballistic missile systems, and related launch sites.\n\nDespite the growing military presence, there has been no official confirmation from President Trump about what he plans to do. Officials familiar with the matter say that while US forces are being prepared for possible action, the President has not yet made a final decision.\n\nPresident Trump has long insisted that Iran completely give up uranium enrichment on its own soil, including agreeing not to enrich any additional uranium. In June last year, during a series of talks between the two countries, Trump ordered strikes on Tehran’s nuclear facilities, a move that completely derailed the negotiations and halted further diplomatic efforts.\n\nIsraeli Prime Minister Benjamin Netanyahu visited the White House last week, urging the US President to ensure that any deal about Iran’s nuclear programme also include steps to neutralise Iran’s ballistic missile programme and end its funding for proxy groups such as Hamas and Hezbollah.\n\nIsraeli forces have also been on high alert for weeks and have stepped up preparations amid fears that tensions could turn into open conflict. According to two Israeli defense officials, a meeting of Israel’s security cabinet was moved from Thursday to Sunday, highlighting growing concern within the Israeli government.\n\nSpeaking at a White House briefing on Wednesday, Press Secretary Karoline Leavitt said that although some may argue in favor of military action, diplomacy remains President Trump’s preferred path.\n\n“The President has always been very clear, whether it’s Iran or any other country around the world, diplomacy is always his first option,” Leavitt said.\n\nShe added that Iran would be “very wise to make a deal with President Trump and with this administration”, signalling that the White House believes there is still time to reach an agreement.\n\nLeavitt also referred to what she described as a “very successful operation in June that targeted Iran’s nuclear facilities”, suggesting that the administration has already shown it is prepared to act if necessary.\n\nIn an effort to negotiate, the two countries held a second round of talks in Geneva Tuesday over Tehran’s nuclear programme. After the talks concluded, Iranian Foreign Minister Abbas Araghchi said that both sides agreed to work further on draft texts for a potential agreement, adding that it has reached an understanding with the United States on the “guiding principles”.\n\n“Ultimately, we were able to reach broad agreement on a set of guiding principles, based on which we will move forward and begin working on the text of a potential agreement,” Araghchi told state TV.\n\n“It was agreed that both sides would work further on draft texts for a potential agreement, after which the drafts would be exchanged, and a date for a third round would be set,” he added.\n\nPresident Trump has said he will be “indirectly involved” in the discussions. Calling the negotiations “very important,” he suggested that Iran may now be more willing to negotiate.\n\n“I don’t think they want the consequences of not making a deal,” Trump said, adding that Iran understands the consequences of taking a tough position, referring to last summer’s US strikes on Iranian nuclear sites.\n\nStay updated with the latest - Click here to follow us on Instagram\n\nVeteran screenwriter Salim Khan has sparked concern after reports of his hospitalisation, prompting family members and close friends to rush to his side.",
  //     pubDate: "2026-02-19T04:55:51.000+00:00",
  //     category: "world",
  //     source: "indianexpress.com",
  //     summary:
  //       "US military forces in the Middle East have been rapidly built up, with officials stating that President Donald Trump could approve strikes against Iran as early as this weekend. The US has positioned military equipment and personnel to target Iran's nuclear facilities, ballistic missile systems, and related launch sites. Despite the growing military presence, President Trump has not yet made a final decision on potential action. Diplomatic efforts continue, with the US and Iran holding talks in Geneva over Tehran's nuclear program. A potential agreement is being worked on, with both sides agreeing to exchange draft texts for a potential agreement.",
  //     retryCount: 0,
  //     lastSummaryAttemptAt: "2026-02-19T05:05:50.193Z",
  //   },
  //   {
  //     link: "https://indianexpress.com/article/world/us-news/skiers-dead-in-california-us-tahoe-avalanche-10540039/",
  //     title:
  //       "US: 8 skiers dead in California avalanche, thick snow makes retrieval impossible",
  //     description: "",
  //     content:
  //       "At least eight skiers have been located dead in the remote Lake Tahoe area of California, US. The eight skiers were earlier reported missing after an avalanche struck on Tuesday. Search crews are still looking for one remaining skier, though authorities now believe that person did not survive either, reported British news website BBC. Officials noted that one of those killed was married to a member of one of the rescue teams, a circumstance they said has made the ongoing operation emotionally difficult.\n\nFifteen people were initially unaccounted for after a slide roughly the size of a football field swept through the Castle Peak zone at about 11:30 AM local time. Six were pulled out alive.\n\nAuthorities said the eight victims’ bodies remain buried under snow and cannot yet be retrieved because of severe conditions. Since the avalanche, another three feet of snow has blanketed the region, Tahoe National Forest supervisor Chris Feutrier was quoted as saying by BBC. He warned that the danger level remains high.\n\nOnce recovery is possible, the remains will be taken to the Placer County morgue, BBC reported. Relatives have been informed, but officials have not released identities. The victims include seven women and two men.\n\nThe search effort involved two teams totaling about 50 personnel navigating harsh weather with specialised gear. Crews reached a point roughly two miles from where survivors had set up temporary shelters and had to ski the rest of the way.\n\nTwo survivors were unable to walk due to injuries and were carried out before being hospitalised with non-life-threatening conditions, BBC quoted an official. Among those rescued were a guide and five clients from a tour run by an agency, Blackbird Mountain. The full group consisted of 11 recreational skiers and four guides and had been heading back at the end of a three-day excursion when the avalanche hit.\n\nAccording to the Sierra Avalanche Center, the slide was rated D2.5 on a destructive scale that runs from D1 to D5, indicating a path exceeding half a mile and debris around 6.5 feet deep.\n\nThe Express Global Desk at The Indian Express delivers authoritative, verified, and context-driven coverage of key international developments shaping global politics, policy, and migration trends. The desk focuses on stories with direct relevance for Indian and global audiences, combining breaking news with in-depth explainers and analysis. A major focus area of the desk is US immigration and visa policy, including developments related to student visas, work permits, permanent residency pathways, executive actions, and court rulings. The Global Desk also closely tracks Canada’s immigration, visa, and study policies, covering changes to study permits, post-study work options, permanent residence programmes, and regulatory updates affecting migrants and international students. All reporting from the Global Desk adheres to The Indian Express’ editorial standards, relying on official data, government notifications, court documents, and on-record sources. The desk prioritises clarity, accuracy, and accountability, ensuring readers can navigate complex global systems with confidence. Core Team The Express Global Desk is led by a team of experienced journalists and editors with deep expertise in international affairs and migration policy: Aniruddha Dhar – Senior Assistant Editor with extensive experience in global affairs, international politics, and editorial leadership. Nischai Vats – Deputy Copy Editor specialising in US politics, US visa and immigration policy, and policy-driven international coverage. Mashkoora Khan – Sub-editor focusing on global developments, with a strong emphasis on Canada visa, immigration, and study-related policy coverage. ... Read More\n\nStay updated with the latest - Click here to follow us on Instagram\n\nTrisha Krishnan, 42, recently shared her gym training with a kettlebell, admitting she used to think walking was sufficient. However, consultant dietitian and fitness expert Garima Goyal explains that for women in their 40s, walking may not be enough. As the body ages, incorporating strength training, core work, and mobility exercises is crucial for maintaining overall health and wellness.",
  //     pubDate: "2026-02-19T04:18:14.000+00:00",
  //     category: "world",
  //     source: "indianexpress.com",
  //     summary:
  //       "At least eight skiers have been found dead in the Lake Tahoe area of California after an avalanche struck on Tuesday. The avalanche initially left 15 people unaccounted for, but six were rescued alive. Search crews are still looking for one remaining skier, who authorities now believe did not survive. The victims' bodies remain buried under snow due to severe conditions, with recovery efforts delayed. The avalanche was rated D2.5 on a destructive scale, indicating a path exceeding half a mile and debris around 6.5 feet deep.",
  //     retryCount: 0,
  //     lastSummaryAttemptAt: "2026-02-19T05:05:50.193Z",
  //   },
  //   {
  //     link: "https://indianexpress.com/article/world/us-news/donald-trump-immigration-policy-detention-without-bond-us-court-10539988/",
  //     title:
  //       "Trump’s hardline immigration policy of detention hit as US federal court intervenes",
  //     description: "",
  //     content:
  //       "A US federal judge has dismissed a ruling that backed a controversial immigration enforcement measure introduced under the Donald Trump administration, reported news agency Reuters. The policy required thousands of detainees to remain in custody without the option of bond.\n\nSunshine Sykes, a US District Judge based in Riverside, California, set aside the earlier decision issued by the Board of Immigration Appeals. She concluded that officials failed to follow a prior directive she had issued that deemed the policy itself unlawful.\n\nEarlier this month, a split federal appeals panel upheld the policy of the Trump government. The 2–1 ruling from the New Orleans-based 5th US Circuit Court of Appeals was the first time an appellate court backed the measure, even as hundreds of lower-court judges across the country found it unlawful.\n\nAs per NBC, US federal immigration law states that “applicants for admission” to the United States must be held in mandatory detention while their cases move through immigration courts and are not eligible for bond hearings.\n\nThe Express Global Desk at The Indian Express delivers authoritative, verified, and context-driven coverage of key international developments shaping global politics, policy, and migration trends. The desk focuses on stories with direct relevance for Indian and global audiences, combining breaking news with in-depth explainers and analysis. A major focus area of the desk is US immigration and visa policy, including developments related to student visas, work permits, permanent residency pathways, executive actions, and court rulings. The Global Desk also closely tracks Canada’s immigration, visa, and study policies, covering changes to study permits, post-study work options, permanent residence programmes, and regulatory updates affecting migrants and international students. All reporting from the Global Desk adheres to The Indian Express’ editorial standards, relying on official data, government notifications, court documents, and on-record sources. The desk prioritises clarity, accuracy, and accountability, ensuring readers can navigate complex global systems with confidence. Core Team The Express Global Desk is led by a team of experienced journalists and editors with deep expertise in international affairs and migration policy: Aniruddha Dhar – Senior Assistant Editor with extensive experience in global affairs, international politics, and editorial leadership. Nischai Vats – Deputy Copy Editor specialising in US politics, US visa and immigration policy, and policy-driven international coverage. Mashkoora Khan – Sub-editor focusing on global developments, with a strong emphasis on Canada visa, immigration, and study-related policy coverage. ... Read More\n\nStay updated with the latest - Click here to follow us on Instagram\n\nRaja Gope, a six-year-old boy from Jharkhand, was separated from his father and mistakenly boarded a train to Kerala. After 14 years, he has been rescued and is set to reunite with his family, who have been struggling since his father's death. The Jharkhand WCD department is coordinating with Kerala to bring Raja home and provide him with rehabilitation and skill training.",
  //     pubDate: "2026-02-19T02:16:05.000+00:00",
  //     category: "world",
  //     source: "indianexpress.com",
  //     summary:
  //       "A US federal judge has dismissed a ruling that supported a Trump-era immigration policy requiring thousands of detainees to remain in custody without bond options. The policy was deemed unlawful by the judge, who cited failure to follow a prior directive. The decision reverses a split federal appeals panel's earlier ruling that upheld the policy. The policy in question requires mandatory detention for applicants for admission to the US while their cases proceed through immigration courts. The judge's decision effectively sets aside the earlier appeals court ruling.",
  //     retryCount: 0,
  //     lastSummaryAttemptAt: "2026-02-19T05:07:53.141Z",
  //   },
  // ];

  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get("page")) || 0;

  const {articles, pagination, loading, error} = useNews(page);

  return (
    <div className="bg-gray-50 min-h-screen flex flex-col">
      <Navbar />
      <Container>
        <h1 className="text-xl font-semibold mt-8 mb-6">Latest News</h1>

        {loading && <p className="text-gray-500">Loading...</p>}
        {error && <p className="text-red-500">{error}</p>}

        {!loading && !error && (
          <>
            <ArticleList articles={articles} />
            <Pagination pagination={pagination}/>
          </>
        )}
      </Container>
      <Footer />
    </div>
  );
}
