window.onload = function () {
	
				if(!flag)
					{
						if(Pass==0 || Skip==0 || Fail==0)
							{
								if(Pass==0 && Fail==0 && Skip>0)
								{
									var r = Raphael("holder"),
									pie = r.piechart(320, 240, 100, [Skip, 0.0001], { colors: ['yellow', 'green'] , legend: ["%%.%%  -  SKIP", "%%.%%  -  OTHERS"], legendpos: "west",  href: ["Pie_Charts/SkipCases.html"],dontsort:true});
								}
								else if(Pass==0 && Skip==0 && Fail>0)
								{
									var r = Raphael("holder"),
									pie = r.piechart(320, 240, 100, [Fail, 0.0001], { colors: ['red', 'green'] , legend: ["%%.%%  -  FAIL", "%%.%%  -  OTHERS"], legendpos: "west",  href: ["Pie_Charts/FailCases.html"],dontsort:true});
								}
								else if(Fail==0 && Skip==0 & Pass>0)
								{
									var r = Raphael("holder"),
									pie = r.piechart(320, 240, 100, [Pass, 0.0001], { colors: ['green', 'red'] , legend: ["%%.%%  -  PASS", "%%.%%  -  OTHERS"], legendpos: "west",  href: ["Pie_Charts/PassCases.html"],dontsort:true});
								}
								else if(Pass==0)
								{
									Pass=0.0001
									var r = Raphael("holder"),
									pie = r.piechart(320, 240, 100, [Fail, Skip, Pass], { colors: ['red', 'yellow', 'green'] , legend: ["%%.%%  -  FAIL", "%%.%%  -  SKIP", "%%.%%  -  PASS"], legendpos: "west",  href: ["Pie_Charts/FailCases.html", "Pie_Charts/SkipCases.html", "Pie_Charts/PassCases.html"],dontsort:true});
								}
								else if(Fail==0)
								{
									Fail=0.0001
									var r = Raphael("holder"),
									pie = r.piechart(320, 240, 100, [Pass, Skip, Fail], { colors: ['green', 'yellow', 'red'] , legend: ["%%.%%  -  PASS", "%%.%%  -  SKIP", "%%.%%  -  FAIL"], legendpos: "west",  href: ["Pie_Charts/PassCases.html", "Pie_Charts/SkipCases.html", "Pie_Charts/FailCases.html"],dontsort:true});
								}
								else if(Skip==0)
								{
									Skip=0.0001
									var r = Raphael("holder"),
									pie = r.piechart(320, 240, 100, [Pass, Fail, Skip], { colors: ['green', 'red', 'yellow'] , legend: ["%%.%%  -  PASS", "%%.%%  -  FAIL", "%%.%%  -  SKIP"], legendpos: "west",  href: ["Pie_Charts/PassCases.html", "Pie_Charts/FailCases.html", "Pie_Charts/SkipCases.html"],dontsort:true});
								}
							}
							else
							{
								var r = Raphael("holder"),
								pie = r.piechart(320, 240, 100, [Pass, Fail, Skip], { colors: ['green', 'red', 'yellow'] , legend: ["%%.%%  -  PASS", "%%.%%  -  FAIL", "%%.%%  -  SKIP"], legendpos: "west",  href: ["Pie_Charts/PassCases.html", "Pie_Charts/FailCases.html", "Pie_Charts/SkipCases.html"],dontsort:true});
							}
						}
				if(flag)
					{
						if(Pass==0 || Skip==0 || Fail==0)
						{
							if(Pass==0 && Fail==0 && Skip>0)
							{
								var r = Raphael("holder"),
								pie = r.piechart(320, 240, 100, [Skip, 0.0001], { colors: ['yellow', 'green'] , legend: ["%%.%%  -  SKIP", "%%.%%  -  OTHERS"], legendpos: "west",  href: ["SkipCases.html"],dontsort:true});
							}
							else if(Pass==0 && Skip==0 && Fail>0)
							{
								var r = Raphael("holder"),
								pie = r.piechart(320, 240, 100, [Fail, 0.0001], { colors: ['red', 'green'] , legend: ["%%.%%  -  FAIL", "%%.%%  -  OTHERS"], legendpos: "west",  href: ["FailCases.html"],dontsort:true});
							}
							else if(Fail==0 && Skip==0 & Pass>0)
							{
								var r = Raphael("holder"),
								pie = r.piechart(320, 240, 100, [Pass, 0.0001], { colors: ['green', 'red'] , legend: ["%%.%%  -  PASS", "%%.%%  -  OTHERS"], legendpos: "west",  href: ["PassCases.html"],dontsort:true});
							}
							else if(Pass==0)
							{
								Pass=0.0001
								var r = Raphael("holder"),
								pie = r.piechart(320, 240, 100, [Fail, Skip, Pass], { colors: ['red', 'yellow', 'green'] , legend: ["%%.%%  -  FAIL", "%%.%%  -  SKIP", "%%.%%  -  PASS"], legendpos: "west",  href: ["FailCases.html", "SkipCases.html", "PassCases.html"],dontsort:true});
							}
							else if(Fail==0)
							{
								Fail=0.0001
								var r = Raphael("holder"),
								pie = r.piechart(320, 240, 100, [Pass, Skip, Fail], { colors: ['green', 'yellow', 'red'] , legend: ["%%.%%  -  PASS", "%%.%%  -  SKIP", "%%.%%  -  FAIL"], legendpos: "west",  href: ["PassCases.html", "SkipCases.html", "FailCases.html"],dontsort:true});
							}
							else if(Skip==0)
							{
								Skip=0.0001
								var r = Raphael("holder"),
								pie = r.piechart(320, 240, 100, [Pass, Fail, Skip], { colors: ['green', 'red', 'yellow'] , legend: ["%%.%%  -  PASS", "%%.%%  -  FAIL", "%%.%%  -  SKIP"], legendpos: "west",  href: ["PassCases.html", "FailCases.html", "SkipCases.html"],dontsort:true});
							}
						}
						else
						{
							var r = Raphael("holder"),
							pie = r.piechart(320, 240, 100, [Pass, Fail, Skip], { colors: ['green', 'red', 'yellow'] , legend: ["%%.%%  -  PASS", "%%.%%  -  FAIL", "%%.%%  -  SKIP"], legendpos: "west",  href: ["PassCases.html", "FailCases.html", "SkipCases.html"],dontsort:true});
						}
					}
                r.text(320, 100, "Interactive Pie Chart").attr({ font: "20px sans-serif" });
                pie.hover(function () {
                    this.sector.stop();
                    this.sector.scale(1.1, 1.1, this.cx, this.cy);

                    if (this.label) {
                        this.label[0].stop();
                        this.label[0].attr({ r: 7.5 });
                        this.label[1].attr({ "font-weight": 800 });
                    }
                }, function () {
                    this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 500, "bounce");

                    if (this.label) {
                        this.label[0].animate({ r: 5 }, 500, "bounce");
                        this.label[1].attr({ "font-weight": 400 });
                    }
                });
            };