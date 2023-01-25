//GC announcements generator
function confAnnounce() {
    var randNum = Math.floor(Math.random() * 3) + 1;
    document.getElementById('announce').innerHTML = announcements[randNum];
}

//announcements array
var announcements = ["Filler",
                    "General Conference leadership sessions released to the public. See what happened on <a href=https://www.mormonnewsroom.org/article/first-presidency-messages-general-conference-leadership-session-april-2019 target=_blank>Thursday</a> and <a href=https://www.mormonnewsroom.org/article/church-leaders-provide-instruction-leadership-sessions-april-2019 target=_blank>Friday</a>.",
                    "Plans for the renovation of the Salt Lake Temple, Temple Square, and the adjoining plaza near the Church Office Building will be announced April 19, 2019. The Manti and Logan Temples will also be renovated in coming years.",
                    "8 temples announced by President Nelson, plus renovations of all Pioneer-era temples. 1) Pago Pago, American Samoa, 2) Okinawa City, 3) Neiafu, Tonga, 4) Tooele Valley, Utah, 5) Moses Lake, Washington, 6) San Pedrosula, Honduras, 7) Antofagasta, Chile and 8) Budapest, Hungary",];


//GC quote generator
function confQuote() {
    var randNum = Math.floor(Math.random() * 91) + 1;
    document.getElementById('quote').innerHTML = quotes[randNum];
}

//quote array
var quotes = ["Filler",
              "\"The warning of our beloved prophet is a further reminder of our individual responsibility to seek and to learn and to teach our families that there is a Father in heaven who loves us.\"<br>- Elder Soares",
             "\"Jesus Christ \'knows and understands our deep sorrows and pains, and He will bless your efforts and dedication to your dear ones if not in this life, in the next life. Remember always that hope is an important part of the gospel plan.\'\"<br>- Elder Soares",
             "\"There is a careful way and a casual way to do everything, including living the gospel.\"<br>- Sister Craven",
             "\"We too are on a track, a covenant path we committed to when we were baptized... Although we may encounter occasional obstacles along the way, this path will keep us moving towards our prized eternal destination if we stay firmly on it.\"<br>- Sister Craven",
             "\"When our worthy desires are not granted in the way we hoped, it may actually be for our ultimate benefit.\"<br>- Elder Hales",
             "Quoting Elder Maxwell: \"Faith also includes trust in God’s timing.\"<br>- Elder Hales",
             "\"A friend, Patricia Parkinson, went blind at age 11. She has felt depression and sadness, but if asked if she is angry says: \'Who would I be angry with? Heavenly Father is in this with me; I am not alone. He is with me all the time.\'\"<br>- Elder Hales",
             "\"We can rationalize all we want, but the fact is, there is not a right way to do the wrong thing.\"<br>- Sister Craven",
             "\"It's not our job to convert people. That's the role of the Holy Ghost. And each person has their agency to choose.\"<br>- Elder Uchtdorf",
             "\"It isn’t necessary for someone to be suffering, like my brother, from a life-threatening disease in order to be in need of ministering service. Those needs come in a variety of shapes, sizes and conditions.\"<br>- Bishop Waddell",
             "\"In modeling our ministering after Jesus Christ, it is important to remember that his efforts to love, to lift, to serve and bless had a higher goal than meeting the immediate need.\"<br>- Bishop Waddell",
             "\"The wise parent will see that opportunity lies in leading each child, and themselves, to accept more fully the Lord’s invitation to come unto Him.\"<br>- President Eyring",
             "\"Building faith in Jesus Christ is the beginning of reversing any spiritual decline in your family and in your home. That faith is more likely to bring repentance than your preaching against each symptom of spiritual decline.\"<br>- President Eyring",
             "\"You will find some of your greatest joys in your efforts to make your home a place of faith in the Lord Jesus Christ and a place that is permeated with love, the pure love of Christ.\"<br>- President Eyring",
             "An apostle said to President Eyring, \"You are worrying about the wrong problem. You just live worthy of the celestial kingdom and the family arrangements will be more wonderful than anything you can imagine.\"",
             "\"He wants all of His children to find peace, joy and happiness in their lives.\"<br>- President Ballard",
             "\"Brothers and sisters, do the best you can do day after day, and before you know it, you will come to realize that your Heavenly Father knows you and that He loves you.\"<br>- President Ballard",
             "\"If we ... neglect the spiritual understanding we can receive through the whisperings and impressions of the Holy Ghost, it is as if we were going through life with only one eye.\"<br>- Elder Held",
             "\"The Spirit speaks to different people in different ways, and He may speak to the same person in different ways at different times. As a result, learning the many ways He speaks to us is a lifelong quest.\"<br>- Elder Homer",
             "\"Feasting upon the scriptures is not just reading them -- it should bring us real joy and build our relationship with the Savior.\"<br>- Elder Wada",
             "\"Some will say, 'You don’t understand my situation.' I may not, but I testify there is one who does understand.\"<br>- Elder Andersen",
             "\"While we celebrate the innovations of science and medicine, the truths of God go far beyond these discoveries.\"<br>- Elder Andersen",
             "\"We humbly declare that there are some things that are completely and absolutely true. These eternal truths are the same for every son and daughter of God. [Jesus Christ] teaches us that it does not matter if we are rich or poor, prominent or unknown, sophisticated or simple.\"<br>- Elder Andersen",
             "\"All Heavenly Father asks of us is to do the very best we can each day.\"<br>- President Ballard",
             "\"We will find peace, joy and happiness in our life when serving the Lord and our neighbors.\"<br>- President Ballard",
             "\"If we pick and choose what we accept in the Proclamation, we cloud our eternal view, putting too much importance on our experience here and now.\"<br>- Elder Andersen",
             "\"There are so many, young and old, who are loyal to the gospel of Jesus Christ even though their own current experience does not fit neatly inside the Family Proclamation.\"<br>- Elder Andersen",
             "\"When we struggle with our own identity and lack of self-esteem, the pleasing word of God in the scriptures will help us know who we really are and give us strength beyond our own\"<br>- Elder Wada",
             "\"If we are not careful, the wrong voices can draw us away from the gospel center to places where faith is difficult to sustain, and we find little more than emptiness, bitterness and dissatisfaction.\"<br>- Elder Homer",
             "\"With the exciting new emphasis on increased gospel learning in the home, it is crucial for us to remember that we are still commanded to go to the house of prayer and offer up thy sacraments upon my holy day.\"<br>- Elder Holland",
             "\"We make an apostolic plea for the reduction of clamor in the sanctuary of our buildings.\"<br>- Elder Holland",
             "\"I fear visitors not of our faith are shocked by what can sometimes be noisy irreverence in a setting that is supposed to be characterized by prayer, revelation, hymns and peace. Perhaps Heaven is a little shocked as well.\"<br>- Elder Holland",
             "\"One way to always remember Him would be to join the Great Physician in His never-ending task of lifting the load from those who are burdened and relieving the pain of those who are distraught.\"<br>- Elder Holland",
             "\"The sacrament should be the sacred focal point of the Sunday worship experience.\"<br>- Elder Holland",
             "\"Most blessings that God desires to give us require action on our part, action based on our faith in Jesus Christ. Faith the Savior is a principle of action and of power.\"<br>- Elder Renlund",
             "\"You don’t earn a blessing. That notion is false. But you have to qualify for it. Our salvation comes only through the merits and grace of Jesus Christ.\"<br>- Elder Renlund",
             "\"The principle of activating blessings that flow from God is eternal. Like those ancient Israelites, we, too, must act on our faith in Jesus Christ to be blessed.\"<br>- Elder Renlund",
             "\"Small actions fuel our ability to walk along the covenant path and lead to the greatest blessings God can offer. But oxygen flows only if we figuratively keep moving our feet.\"<br>- Elder Renlund",
             "\"One of the fundamental needs we have in order to grow is to stay connected to our source of light, Jesus Christ. He is the source of our power--the light and life of the world.\"<br>- Sister Eubank",
             "\"When expectations overwhelm us, we can step back and ask Heavenly Father what to let go of. Part of our life experience is knowing what not to do.\"<br>- Sister Eubank",
             "\"It's an unwavering requirement of Christian disciples and Latter-day Saints to show true love to one another.\"<br>- Sister Eubank",
             "\"The miracle of His grace is that when we repent of our sins, His scarlet blood returns us to purity.\"<br>- Sister Eubank",
             "\"If you feel that the beacon of your testimony is sputtering and darkness is closing in, take courage. Keep your promises to God.\"<br>- Sister Eubank",
             "\"I testify you are beloved. The Lord knows how hard you are trying. You are making progress. Keep going . . . Your work is not in vain. You are not alone. … He is surely with you.\"<br>- Sister Eubank",
             "\"Love of the Savior and love our fellow men and women is the primary attribute and motive for ministering and the spiritual purposes we were charged to undertake by our beloved prophet.\"<br>- Elder Cook",
             "\"I promise that lovingly performing ordinances for ancestors will strengthen and protect our youth and families in a world that is becoming increasingly evil.\"<br>- Elder Cook",
             "\"Women and men have unique roles as outlined in The Family: A Proclamation to the World, but their stewardships are equal in value and importance.\"<br>- Elder Cook",
             "\"One adjustment that will benefit almost any family is to make the internet, social media and television a servant instead of a distraction, or even worse, a master.\"<br>- Elder Cook",
             "\"Teaching in our homes needs to be clear and compelling, but also spiritual, joyful and full of love.\"<br>- Elder Cook",
             "\"Let us do all we can to relieve suffering and sorrow now, and let us devote ourselves more diligently to the preparations needed for the day when pain and evil are ended altogether, when \'Christ shall reign personally upon the earth.\'\"<br>- Elder Christofferson",
             "\"The Resurrection confirms the divinity of Jesus Christ and the reality of God, the father. Our thoughts turn to the Savior and we ponder ... the infinite virtue of His great atoning sacrifice.\"<br>- Elder Christofferson",
             "\"The Spirit made clear to me that The Church of Jesus Christ of Latter-day Saints is uniquely empowered and commissioned to accomplish the necessary preparations for the Lord’s Second Coming. Indeed, it was restored for that purpose.\"<br>- Elder Christofferson",
             "\"Crucial for the Lord’s return is the presence on the earth of a people prepared to receive Him at His coming.\"<br>- Elder Christofferson",
             "\"Zion is the pure in heart, a people of one heart and one mind, dwelling in righteousness with no poor among them.\"<br>- Elder Christofferson",
             "\"It is an infinite Atonement because it encompasses and circumscribes every sin and weakness, as well as every abuse or pain caused by others.\"<br>- Brother Callister",
             "\"One reason it is so essential to understand the Savior’s Atonement and its infinite implications is that with increased understanding comes an increased desire to forgive ourselves and others.\"<br>- Brother Callister",
             "\"The Savior's Atonement gives us life for death, beauty for ashes, healing for hurt and perfection for weakness. It is heaven's antidote to the obstacles and struggles of this world.\"<br>- Brother Callister",
             "\"Now, as president of His Church, I plead with you who have distanced yourselves from the Church and with you who have not yet really sought to know that the Savior’s Church has been restored. Do the spiritual work to find out for yourselves and please do it now. Time is running out.\"<br>- President Nelson",
             "President Nelson of his late daughter: \"We worked together, sang together, and often skied together,\" but before her death \"we talked of things that matter most, such as covenants, ordinances, obedience, faith, family, fidelity, love, and eternal life.\"",
             "\"While [the Savior’s] resurrection assures that every person who ever lived will indeed be resurrected and live forever, much more is required if we want to have the high privilege of exaltation. While salvation is an individual matter, exaltation is a family matter. So what is required for a family to be exalted forever? We qualify for that privilege by making covenants with God, keeping those covenants and receiving essential ordinances.\"<br>- President Nelson",
             "To those who have not received saving ordinances: \"In this life, you've never settled for second best in anything. Yet, as you resist fully embracing the restored gospel of Jesus Christ, you are choosing to settle for second best.\"<br>- President Nelson",
             "\"If you truly love your family and if you desire to be exalted with them throughout eternity, pay the price now through serious study and fervent prayer to know these eternal truths and then abide by them.\"<br>- President Nelson",
             "\"In the absence of experiences with God, one can doubt the existence of God. So put yourself in a position to begin having experiences with him.\"<br>- President Nelson",
             "\"Just as reading and learning about muscles is not enough to build muscle, reading and learning about faith without adding action is insufficient to build faith.\"<br>- Elder Villar",
             "\"We all need repentance. Repentance begins with the Savior and is a joy not a burden.\"<br>- President Oaks",
             "\"We need to partake of the sacrament each Sabbath day. In that ordinance we make covenants and receive blessings that help us overcome all acts and desires that block us from the perfection our Savior invites us to achieve. As we deny ourselves of all ungodliness, and love God with all our might, mind, and strength, then, we may be perfect in Christ and be sanctified through the shedding of His blood to become holy, without spot. What a promise!\"<br>- President Oaks",
             "\"When we repent, we have the Lord's assurance that our sins, including our acts and desires, will be cleansed, and our merciful final judge will remember them no more.\"<br>- President Oaks",
             "\"My message today is one of hope for all of us, including those who have lost their membership in the Church by excommunication or name removal. We are all sinners who can be cleansed by repentance. Because of God's plan and the Atonement of Jesus Christ, I testify with a perfect brightness of hope that God loves us, and we can be cleansed by the process of repentance.\"<br>- President Oaks",
             "\Our Savior has the power and stands ready to cleanse us from evil. Now is the time to seek His help to repent of our wicked or unseemly desires and thoughts, to be clean and prepared to stand before God at the final judgement.\"<br>- President Oaks",
             "\"God's shepherds are to strengthen, heal, bind up that which is broken, bring again that which was driven away, seek that which was lost.\"<br>- Elder Gong",
             "\"Our Savior reaches out to the one and to the ninety-and-nine, often at the same time.\"<br>- Elder Gong",
             "\"As Christ freely dedicated His will to the will of the Father, so we reverently take upon us His name. We gladly seek to join His work of gathering and ministering to all of God’s children.\"<br>- Elder Gong",
             "\"Our Good Shepherd calls us in His voice and in His name. He seeks, gathers and comes to His people.\"<br>- Elder Gong",
             "\"Each member of The Church of Jesus Christ of Latter-day Saints has an individual responsibility to learn and live the Lord's teachings and to receive by proper authority the ordinances of salvation and exaltation.\"<br>- Elder Bednar",
             "\"We cannot rely exclusively upon or borrow gospel light and knowledge from other people — even those whom we love and trust.\"<br>- Elder Bednar",
             "\"Making our homes sanctuaries wherein we can stand in holy places is essential in these latter days.\"<br>- Elder Bednar",
             "\"The ultimate missionary training center is in our homes.\"<br>- Eldar Bednar",
             "\"In Christ and through Christ, there is always hope--no matter what, no matter where.\"<br>- Elder McKay",
             "\"Our mere preservation is a tender and powerful manifestation of the immediate goodness of God.\"<br>- Elder Bednar",
             "\"The temptations [Satan] puts in our path cause us to abuse our bodies or the bodies of others. Because Satan is miserable without a body, he wants us to be miserable because of ours.\"<br>- President Nelson",
             "\"There's nothing bad about playing video games or texting or watching TV or talking on a cell phone. But ... if we spend time doing one thing, we lose the opportunity to do another.\"<br>- President Oaks",
             "\"We love you, and our Heavenly Father and His Son, Jesus Christ, love you.\"<br>- President Oaks",
             "\"Our present and our future will be happier if we are always conscious of the future. As we make current decisions, we should always be asking, \'Where will this lead?\'\"<br>- President Oaks",
             "\"It took faith in Jesus Christ to sustain the plan of happiness, and Jesus Christ’s place in it, when you knew so little of the challenges that you would face in mortality.\"<br>- President Eyring",
             "\"It is my prayer that we will accept the Lord’s invitation to be united with Him in our priesthood quorums so that each quorum might be a place of belonging, a place of gathering, a place that grows.\"<br>- Elder Cook",
             "\"We holders of the priesthood also have a team, a quorum, and a playbook, the holy scriptures and words of modern prophets. You know what temptations you are vulnerable to and you can predict how the adversary will try to derail and dishearten you. With the game plan, a playbook and a firm commitment to execute your role, you will find that temptation has less control over you. You will already have made the decision how you will react and what you will do.\"<br>- Elder Stevenson",
             "\"Do you recognize your higher and holier identity as a son of God—a bearer of His holy priesthood?\"<br>- Elder Stevenson",
             "\"I thought fame, fortune, & an MVP award would make me happy. Something was missing. So, I...prepared and [entered] the temple. I am now on a path to [return] to my Heavenly Father and have an eternal family...the greatest joy in the world!\"<br>- Elder Stevenson quoting Bryce Harper",
             "Elder Gary E Stevenson on the Latter-Day Saint athletes: \"I found it interesting that they do not identify themselves by what they do, as professional athletes, rather by who they are, as sons of a loving Heavenly Father and holders of the priesthood of God.\"",
             "\"Love and care for your wife. Become one with her. Be her partner. Make it easy for her to want to be yours. No other interest in life should take priority over building an eternal relationships with her.\"<br>- President Nelson",
             "\"In this reverence for our bodies, brethren, I think we can do better and be better.\"<br>- President Nelson",];


function startConf(){
  confAnnounce();
  confQuote();
}
